package com.investment.bank.fee.service;

import java.util.List;

import com.investment.bank.fee.exceptions.FeeException;
import com.investment.bank.fee.model.Transaction;

public interface TransactionService {
	
	List<Transaction> getTransactionDetails() throws FeeException;

}
