package org.atm.withdraw.exceptions;

public class InsufficientBalanceException extends RuntimeException {
	
	public InsufficientBalanceException(double currentBalance, long withdrawAmount) {
		super("Insufficient Balance");
	}
}
