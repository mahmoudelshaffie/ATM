package org.atm;

import org.atm.withdraw.exceptions.InsufficientAvailableMoneyException;

public class ATMStateService {
	
	private volatile long totalAvailable = 0;
	
	private ATMStateService() {
	}
	
	public void setTotalAvailabiltiy(long totalAvailable) {
		this.totalAvailable = totalAvailable;
	}
	
	public long getTotalAvailabiltiy() {
		return totalAvailable;
	}
	
	public synchronized void withdraw(long amount) {
		if (totalAvailable < amount) {
			throw new InsufficientAvailableMoneyException(totalAvailable, amount);
		}
		this.totalAvailable = this.totalAvailable - amount;
	}
	
	public synchronized void deposite(long amount) {
		this.totalAvailable = this.totalAvailable + amount;
	}
	
	public static ATMStateService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ATMStateService();
		}
		return INSTANCE;
	}
	
	private static ATMStateService INSTANCE = null;
}
