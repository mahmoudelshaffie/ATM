package org.atm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atm.withdraw.CashWithdrawTransaction;
import org.junit.Before;
import org.junit.Test;

public class ATMTCashBoxesRackServiceUnitTest {

	private final Long ACCOUNT_NO = 123454646L;
	private final Short PIN = 1234;
	private Map<Short, Long> FULL_NOTES_AVAILABILITY;
	private Map<Short, Long> FULL_COINS1_AVAILABILITY;
	private Map<Short, Long> FULL_COINS2_AVAILABILITY;

	private List<ATMCashBox> notesBoxes = new ArrayList<>();
	private List<ATMCashBox> coinsBoxes = new ArrayList<>();
	private ATMCashBox noteBox;
	private ATMCashBox coinBox1;
	private ATMCashBox coinBox2;

	@Before
	public void before() {
		FULL_NOTES_AVAILABILITY = new HashMap<>();
		FULL_NOTES_AVAILABILITY.put((short) 500, 20L);
		FULL_NOTES_AVAILABILITY.put((short) 200, 40L);
		FULL_NOTES_AVAILABILITY.put((short) 100, 40L);
		FULL_NOTES_AVAILABILITY.put((short) 50, 30L);
		noteBox = new ATMCashBox(FULL_NOTES_AVAILABILITY);
		notesBoxes.add(noteBox);

		FULL_COINS1_AVAILABILITY = new HashMap<>();
		FULL_COINS1_AVAILABILITY.put((short) 1, 20L);
		FULL_COINS1_AVAILABILITY.put((short) 10, 20L);
		coinBox1 = new ATMCashBox(FULL_COINS1_AVAILABILITY);
		coinsBoxes.add(coinBox1);

		FULL_COINS2_AVAILABILITY = new HashMap<>();
		FULL_COINS2_AVAILABILITY.put((short) 5, 20L);
		FULL_COINS2_AVAILABILITY.put((short) 20, 20L);
		coinBox2 = new ATMCashBox(FULL_COINS2_AVAILABILITY);
		coinsBoxes.add(coinBox2);
	}

	@Test
	public void testDoWithdrawTransactionWithAvailableAmountShouldBeWithDrawWithLeastAmountOfCashSuccessfully() {
		ATMCashBoxesRackService target = new ATMCashBoxesRackService(notesBoxes, coinsBoxes);
		long amount = 1886L;
		CashWithdrawTransaction tx = new CashWithdrawTransaction(amount, ACCOUNT_NO, PIN);
		target.doWithdrawTransaction(tx);
		Long actual500 = tx.getCashWithdrawl().getNoteAmount((short) 500);
		Long expected500 = 3L;
		assertEquals(expected500, actual500);
		Long actual200 = tx.getCashWithdrawl().getNoteAmount((short) 200);
		Long expected200 = 1L;
		assertEquals(expected200, actual200);
		Long actual_100 = tx.getCashWithdrawl().getNoteAmount((short) 100);
		Long expected_100 = 1L;
		assertEquals(expected_100, actual_100);
		Long actual50 = tx.getCashWithdrawl().getNoteAmount((short) 50);
		Long expected50 = 1L;
		assertEquals(expected50, actual50);
		Long actual20 = tx.getCashWithdrawl().getCoinAmount((short) 20);
		Long expected20 = 1L;
		assertEquals(expected20, actual20);
		Long actual_10 = tx.getCashWithdrawl().getCoinAmount((short) 10);
		Long expected_10 = 1L;
		assertEquals(expected_10, actual_10);
		Long actual5 = tx.getCashWithdrawl().getCoinAmount((short) 5);
		Long expected5 = 1L;
		assertEquals(expected5, actual5);
		Long actual_1 = tx.getCashWithdrawl().getCoinAmount((short) 1);
		Long expected_1 = 1L;
		assertEquals(actual_1, expected_1);
	}

	@Test
	public void testDoWithdrawTransactionWithAmountOfAllAvailableNotesShouldBeWithDrawedOneOfEachAvailableNoteSuccessfully() {
		ATMCashBoxesRackService target = new ATMCashBoxesRackService(notesBoxes, coinsBoxes);
		long amount = 135L;
		CashWithdrawTransaction tx = new CashWithdrawTransaction(amount, ACCOUNT_NO, PIN);
		tx.addNote((short) 100, 1L);
		tx.addCoin((short) 20, 1L);
		tx.addCoin((short) 10, 1L);
		tx.addCoin((short) 5, 1L);
		target.undoWithdrawTransaction(tx);
		Long actual20 = coinBox2.getAvailabilityOf((short) 20);
		Long expected20 = 21L;
		Long actual_100 = noteBox.getAvailabilityOf((short) 100);
		Long expected_100 = 41L;
		assertEquals(expected_100, actual_100);
		assertEquals(expected20, actual20);
		Long actual_10 = coinBox1.getAvailabilityOf((short) 10);
		Long expected_10 = 21L;
		assertEquals(expected_10, actual_10);
		Long actual5 = coinBox2.getAvailabilityOf((short) 5);
		Long expected5 = 21L;
		assertEquals(expected5, actual5);
		
	}

}
