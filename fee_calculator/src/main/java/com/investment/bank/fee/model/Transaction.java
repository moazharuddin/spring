package com.investment.bank.fee.model;

import java.util.Comparator;
import java.util.Date;

import com.investment.bank.fee.constants.FeeConstants.Priority;

public class Transaction implements Comparator<Transaction>{
	
	private String transactionId;
	private String clientId;
	private String securityId;
	private String transactionType;
	private Date transactionDate;
	private Double marketValue;
	private Priority priority;
	private Integer processingFee;
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}
	public Integer getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(Integer processingFee) {
		this.processingFee = processingFee;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	
	@Override
	public int compare(Transaction o1, Transaction o2) {
		int client = o1.clientId.compareTo(o2.clientId);
		
		if(client == 0) {
			int type = o1.getTransactionType().compareTo(o2.getTransactionType());
			if(type == 0) {
				int date = o1.getTransactionDate().compareTo(o2.transactionDate);
				if(date == 0) {
					return o1.getPriority().compareTo(o2.getPriority());
				} else {
					return date;
				}
			} else {
				return type;
			}
		}
		
		return client;
	}
	

}
