package org.atm.withdraw;

import java.util.SortedMap;
import java.util.TreeMap;

import org.atm.Orders;

public class CashWithdrawTransaction {
	private Long accountNo;
	private Short pin;
	private final long txAmount;
	private long txWithdrawedAmount = 0;
	
	// No need for synchronized maps, since it wouldn't be shared cross multiple threads
	private SortedMap<Short, Long> notes = new TreeMap<Short, Long>(Orders.DESC_ORDER);
	private SortedMap<Short, Long> coins = new TreeMap<Short, Long>(Orders.DESC_ORDER);
	
	public CashWithdrawTransaction(long amount, Long accountNo, Short pin) {
		this.txAmount = amount;
		this.accountNo = accountNo;
		this.pin = pin;
	}
	
	public long getTxAmount() {
		return txAmount;
	}
	
	public Long getAccountNo() {
		return accountNo;
	}
	
	public Short getPin() {
		return pin;
	}
	
	public boolean hasRemaining() {
		return txWithdrawedAmount < txAmount;
	}
	
	public void addNote(Short noteValue, Long amount) {
		Long noteAmount = notes.get(noteValue);
		if (noteAmount == null) {
			noteAmount = amount;
		} else {
			noteAmount += amount;
		}
		notes.put(noteValue, noteAmount);
		
		txWithdrawedAmount += (noteValue * amount);
	}
	
	public void addCoin(Short coinValue, Long amount) {
		Long coinAmount = coins.get(coinValue);
		if (coinAmount == null) {
			coinAmount = amount;
		} else {
			coinAmount += amount;
		}
		coins.put(coinValue, coinAmount);
		
		txWithdrawedAmount += (coinValue * amount);
	}
	
	public Long removeNote(Short noteValue) {
		Long amount = this.notes.remove(noteValue);
		if (amount != null) {
			this.txWithdrawedAmount -= (noteValue * amount);
		}
		return amount;
	}
	
	public Long removeCoin(Short coinValue) {
		Long amount = this.coins.remove(coinValue);
		if (amount != null) {
			this.txWithdrawedAmount -= (coinValue * amount);
		}
		return amount;
	}
	
	public long getRemainingWithdrawAmount() {
		return txAmount - txWithdrawedAmount;
	}
	
	public CashWithdrawl getCashWithdrawl() {
		return new CashWithdrawl(notes, coins);
	}
	
	public void undo() {
		this.notes.clear();
		this.coins.clear();
	}
	
}
