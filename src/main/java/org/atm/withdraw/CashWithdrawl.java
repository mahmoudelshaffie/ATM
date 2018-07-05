package org.atm.withdraw;

import java.util.Iterator;
import java.util.SortedMap;

public class CashWithdrawl {
	private SortedMap<Short, Long> notes;
	private SortedMap<Short, Long> coins;

	public CashWithdrawl(SortedMap<Short, Long> notes, SortedMap<Short, Long> coins) {
		this.notes = notes;
		this.coins = coins;
	}

	public Iterator<Short> getWithdrawedNotes() {
		return notes.keySet().iterator();
	}

	public Iterator<Short> getWithdrawedCoins() {
		return coins.keySet().iterator();
	}

	public Long getNoteAmount(Short value) {
		return notes.get(value);
	}

	public Long getCoinAmount(Short value) {
		return coins.get(value);
	}
	
	public SortedMap<Short, Long> getCoins() {
		return coins;
	}
	
	public SortedMap<Short, Long> getNotes() {
		return notes;
	}
}
