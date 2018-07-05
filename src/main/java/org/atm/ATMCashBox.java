package org.atm;

import static org.atm.Orders.DESC_ORDER;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.atm.withdraw.CashWithdrawTransaction;
import org.atm.withdraw.exceptions.InsufficientAvailableMoneyException;

public class ATMCashBox {

	private volatile long totalAvailable;
	private SortedMap<Short, Long> availableCash;

	public ATMCashBox(Map<Short, Long> availability) {
		totalAvailable = 0;
		availableCash = new ConcurrentSkipListMap<Short, Long>(DESC_ORDER); // Need to be synchronized
		for (Short value : availability.keySet()) {
			Long amount = availability.get(value);
			availableCash.put(value, amount);
			totalAvailable += (value * amount);
		}
	}

	private synchronized void changeTotalAvailability(long amount) {
		totalAvailable += amount;
	}

	public long getTotalAvailability() {
		return this.totalAvailable;
	}
	
	public long getAvailabilityOf(Short value) {
		return availableCash.get(value);
	}
	
	public Iterator<Short> getAvailableValues() {
		return availableCash.keySet().iterator();
	}
	
	public void withdraw(Short value, Long amount) {
		long available = availableCash.get(value);
		if (amount != 0 && available > amount) {
			available -= amount;
			// Update availability
			availableCash.put(value, available);
			// Decrease Total Availability
			changeTotalAvailability(value * amount * -1);
		} else {
			throw new InsufficientAvailableMoneyException(available, amount);
		}
	}
	
	public void deposite(Short value, Long amount) {
		long available = availableCash.get(value);
		available += amount;
		availableCash.put(value, available);
		changeTotalAvailability(value * amount);
	}
}
