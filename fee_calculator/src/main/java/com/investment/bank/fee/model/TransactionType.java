package com.investment.bank.fee.model;

public enum TransactionType {

	Buy("Buy", 1), Sell("Sell", 2), Deposit("Deposit", 3), Withdraw("Withdraw", 4);
	
	private int type;
	private String name;
	
	TransactionType(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
}
