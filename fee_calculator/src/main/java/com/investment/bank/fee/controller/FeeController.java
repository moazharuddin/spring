package com.investment.bank.fee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.bank.fee.exceptions.FeeException;
import com.investment.bank.fee.model.Transaction;
import com.investment.bank.fee.service.TransactionService;

@RestController
@RequestMapping(value ="/fee")
public class FeeController {
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping(path = "/transactions")
	public List<Transaction> getTransactions() throws FeeException{
		
		List<Transaction> transactionList = transactionService.getTransactionDetails();
		
		return transactionList;
	}

}
