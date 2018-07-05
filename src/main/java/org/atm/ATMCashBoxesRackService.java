package org.atm;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.atm.withdraw.CashWithdrawTransaction;
import org.atm.withdraw.CashWithdrawl;
import org.atm.withdraw.IWithdrawTransactionService;


public class ATMCashBoxesRackService implements IWithdrawTransactionService {
	
	private SortedMap<Short, ATMCashBox> notesRack;
	private SortedMap<Short, ATMCashBox> coinsRack;
	
	private void initRackWith(SortedMap<Short, ATMCashBox> targetRack, List<ATMCashBox> boxes) {
		for (ATMCashBox box: boxes) {
			Iterator<Short> values = box.getAvailableValues();
			while (values.hasNext()) {
				Short value = values.next();
				targetRack.put(value, box);
			}
		}
	}
	
	public ATMCashBoxesRackService(List<ATMCashBox> noteBoxes, List<ATMCashBox> coinsBoxes) {
		this.notesRack = new TreeMap<>(Orders.DESC_ORDER);
		this.coinsRack = new TreeMap<>(Orders.DESC_ORDER);
		initRackWith(notesRack, noteBoxes);
		initRackWith(coinsRack, coinsBoxes);
	}
	
	protected void withdrawNotes(CashWithdrawTransaction tx) {
		for (Short note : notesRack.keySet()) {
			ATMCashBox notesBox = notesRack.get(note);
			if (tx.hasRemaining()) {
				long remaining = tx.getRemainingWithdrawAmount();
				long amount = remaining / note;
				long available = notesBox.getAvailabilityOf(note);
				
				if (amount != 0 && available > 0) {
					if (available < amount) {
						amount = available;
					}
					notesBox.withdraw(note, amount);
					
					tx.addNote(note, amount);
				}
			} else {
				break;
			}		
			
		}
	}
	
	protected void withdrawCoins(CashWithdrawTransaction tx) {
		for (Short coin : coinsRack.keySet()) {
			ATMCashBox coinsBox = coinsRack.get(coin);
			if (tx.hasRemaining()) {
				long remaining = tx.getRemainingWithdrawAmount();
				long amount = remaining / coin;
				long available = coinsBox.getAvailabilityOf(coin);
				
				if (amount != 0 && available > 0) {
					if (available < amount) {
						amount = available;
					}
					coinsBox.withdraw(coin, amount);
					
					tx.addCoin(coin, amount);
				}
			} else {
				break;
			}		
			
		}
	}
	
	protected void depositeNotes(CashWithdrawl withdrawl) {
		Iterator<Short> txNotes = withdrawl.getWithdrawedNotes();
		while(txNotes.hasNext()) {
			Short note = txNotes.next();
			ATMCashBox noteBox = notesRack.get(note);
			Long amount = withdrawl.getNoteAmount(note);
			noteBox.deposite(note, amount);
		}
	}
	
	protected void depositeCoins(CashWithdrawl withdrawl) {
		Iterator<Short> txCoins = withdrawl.getWithdrawedCoins();
		while(txCoins.hasNext()) {
			Short coin = txCoins.next();
			ATMCashBox coinBox = coinsRack.get(coin);
			Long amount = withdrawl.getCoinAmount(coin);
			coinBox.deposite(coin, amount);
		}
	}

	@Override
	public void doWithdrawTransaction(CashWithdrawTransaction tx) {
		withdrawNotes(tx);
		withdrawCoins(tx);
	}

	@Override
	public void undoWithdrawTransaction(CashWithdrawTransaction tx) {
		CashWithdrawl withdrawl = tx.getCashWithdrawl();
		depositeNotes(withdrawl);
		depositeCoins(withdrawl);
		tx.undo();
	}
	
	public Long getTotalAvailability() {
		Long totalAvailability = 0L;
		
		for (ATMCashBox box: notesRack.values()) {
			totalAvailability += box.getTotalAvailability();
		}
		
		for (ATMCashBox box: coinsRack.values()) {
			totalAvailability += box.getTotalAvailability();
		}
		
		return totalAvailability;
	}
}
