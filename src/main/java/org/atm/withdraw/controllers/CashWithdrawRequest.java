package org.atm.withdraw.controllers;

public class CashWithdrawRequest {
	
	private Long accountNo;
	private String pin;
	private Long amount;
	
	public CashWithdrawRequest() {
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	
}
