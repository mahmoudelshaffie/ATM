package org.atm.withdraw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.atm.withdraw.CashWithdrawTransaction;
import org.junit.Test;

public class CashWithdrawTransactionUnitTest {

	private final Long ACCOUNT_NO = 123454646L;
	private final Short PIN = 1234;
	
	@Test
	public void testAddCoinShouldBeAddedAndRemainingAmountDecreasedSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short coinValue = 5;
		Long amount = 10L;
		target.addCoin(coinValue, amount);
		Long expectedRemaining = 950L;
		Long actual = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actual);
	}
	
	@Test
	public void testAddNoteShouldBeAddedRemainingAmountDecreasedSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short noteValue = 100;
		Long amount = 2L;
		target.addNote(noteValue, amount);
		Long expectedRemaining = 800L;
		Long actual = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actual);
	}
	
	@Test
	public void testRemoveCoinAfterAdding3ShouldBeRemovedAndRemainingAmountIncreasedSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short coinValue = 10;
		Long amount = 5L;
		target.addCoin(coinValue, amount);
		Long expectedRemaining = 950L;
		Long actualRemaining = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actualRemaining);
		
		Long actualRemovedAmount = target.removeCoin(coinValue);
		assertEquals(amount, actualRemovedAmount);
		expectedRemaining = 1000L;
		actualRemaining = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actualRemaining);
	}
	
	@Test
	public void testRemoveCoinWithUnExistCoinShouldReturnNullAndRemainingAmountUntouchedSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short coinValue = 10;
		Long actualRemovedAmount = target.removeCoin(coinValue);
		assertNull(actualRemovedAmount);
		Long expectedRemaining = 1000L;
		Long actualRemaining = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actualRemaining);		
	}
	
	@Test
	public void testRemoveNoteWithUnExistNoteShouldReturnNullAndRemainingAmountUntouchedSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short noteValue = 100;
		Long actualRemovedAmount = target.removeNote(noteValue);
		assertNull(actualRemovedAmount);
		Long expectedRemaining = 1000L;
		Long actualRemaining = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actualRemaining);		
	}
	
	@Test
	public void testHasRemainingGetRemainingAfterAdding5CoinsOf10ShouldReturnTrueSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short coinValue = 10;
		Long amount = 5L;
		target.addCoin(coinValue, amount);
		Long expectedRemaining = 950L;
		Long actual = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actual);
		assertTrue(target.hasRemaining());
	}
	
	@Test
	public void testHasRemainingGetRemainingAfterAdding5NotesOf200ShouldReturnFalseSuccessfully() {
		CashWithdrawTransaction target = new CashWithdrawTransaction(1000L, ACCOUNT_NO, PIN);
		Short noteValue = 200;
		Long amount = 5L;
		target.addNote(noteValue, amount);
		Long expectedRemaining = 0L;
		Long actual = target.getRemainingWithdrawAmount();
		assertEquals(expectedRemaining, actual);
		assertFalse(target.hasRemaining());
	}
}
