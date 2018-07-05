package org.atm.withdraw.exceptions;

public class InsufficientAvailableMoneyException extends RuntimeException {

	public InsufficientAvailableMoneyException(long currentAvailability, long amount) {
		
	}
}
