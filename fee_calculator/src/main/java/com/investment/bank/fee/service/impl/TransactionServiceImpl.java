package com.investment.bank.fee.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.investment.bank.fee.constants.FeeConstants;
import com.investment.bank.fee.constants.FeeConstants.Fees;
import com.investment.bank.fee.constants.FeeConstants.FileType;
import com.investment.bank.fee.constants.FeeConstants.Priority;
import com.investment.bank.fee.exceptions.FeeException;
import com.investment.bank.fee.model.Transaction;
import com.investment.bank.fee.model.TransactionType;
import com.investment.bank.fee.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	
	public List<Transaction> transactionList = new ArrayList<Transaction>();
	
	@Autowired
	Environment env;
	

	@Override
	public List<Transaction> getTransactionDetails() throws FeeException {
		String fileName = FeeConstants.FILE;
		String password = env.getProperty("excel.file.password");
		//TODO: check file type and process accordingly
		//if(fileName.contains(FileType.XSLX.getValue()))
		return getFeeCalculationXlsx(fileName, password);
	}


	private List<Transaction> getFeeCalculationXlsx(String file, String password) throws FeeException {
		File xslxFile = null;
		Workbook workbook = null;
		NPOIFSFileSystem fileSystem = null;
		InputStream dataStream = null;
		try {
			xslxFile = ResourceUtils.getFile(file);
            fileSystem = new NPOIFSFileSystem(xslxFile);
            EncryptionInfo info = new EncryptionInfo(fileSystem);
            Decryptor decryptor = Decryptor.getInstance(info);
            
            if (null == decryptor || !decryptor.verifyPassword(password)) {
               throw new FeeException("Unable to read excel, password is wrong");
            }
             
            dataStream = decryptor.getDataStream(fileSystem);
             
            workbook = new XSSFWorkbook(dataStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            //Skip Headers
            Row headerRow = iterator.next();
            
            while (iterator.hasNext()) {
            	Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Transaction transaction = new Transaction();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    switch (columnIndex) {
                    case 0:
                        transaction.setTransactionId(cell.getStringCellValue());
                        break;
                    case 1:
                        transaction.setClientId(cell.getStringCellValue());
                        break;
                    case 2:
                        transaction.setSecurityId(cell.getStringCellValue());
                        break;
                    case 3:
                        transaction.setTransactionType(cell.getStringCellValue());
                        break;
                    case 4:
                        transaction.setTransactionDate(cell.getDateCellValue());
                        break;
                    case 5:
                        transaction.setMarketValue(cell.getNumericCellValue());
                        break;
                    case 6:
						if (cell.getStringCellValue().equals("Y")) {
							transaction.setPriority(Priority.Normal);
						} else {
							transaction.setPriority(Priority.High);
						}
                        break;
                    }
                }
                transactionList.add(calculateTransactionFee(transaction));
            }
            
        } catch (GeneralSecurityException | IOException ex) {
            ex.printStackTrace();
            throw new FeeException("Couldn't able to read the excel, error: "+ex.getMessage());
        } finally {
            try {
            	fileSystem.close();
				dataStream.close();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new FeeException("Unable to close files, error: "+e.getMessage());
			}    
		}
		return transactionList;
	}
	
	//Calculate Processing fee based on the criteria
	private Transaction calculateTransactionFee(Transaction transaction) {
		if(isIntradayTransaction(transaction)){
			transaction.setProcessingFee(Fees.Ten.getFee());
		} else {

			if(transaction.getPriority() == Priority.Normal){
				transaction.setProcessingFee(Fees.Five_Hundred.getFee());

			} else{				
				if(transaction.getTransactionType() == TransactionType.Sell.getName() ||
						transaction.getTransactionType() == TransactionType.Withdraw.getName()){

					transaction.setProcessingFee(Fees.Hundred.getFee());

				} else if(transaction.getTransactionType() == TransactionType.Buy.getName() ||
						transaction.getTransactionType() == TransactionType.Deposit.getName()){

					transaction.setProcessingFee(Fees.Fifty.getFee());			
				}

			}

		}
		return transaction;
	}
	
	//Check if a transaction isIntraDay Transaction
	private boolean isIntradayTransaction(Transaction transaction) {
		boolean isIntraDayTransaction= false;
		Transaction temp = null;
		if(transactionList.size() > 0 ){
			for (Transaction trans : transactionList) {
				if(trans.getClientId().equals(transaction.getClientId())&&
						trans.getSecurityId().equals(transaction.getSecurityId()) &&
						trans.getTransactionDate().equals(transaction.getTransactionDate())){
					if((trans.getTransactionType()==TransactionType.Buy.getName() && 
							transaction.getTransactionType()==TransactionType.Sell.getName()) ||
							(trans.getTransactionType()==TransactionType.Sell.getName() && 
							transaction.getTransactionType()==TransactionType.Buy.getName())){
						isIntraDayTransaction= true;
						temp= trans;						
						break;
					}
				}

			}

			if(temp!=null){
				transactionList.remove(temp);
				temp.setProcessingFee(Fees.Ten.getFee());
				transactionList.add(temp);
			}

		} else {
			isIntraDayTransaction= false;
		}

		return isIntraDayTransaction;
	}


}
