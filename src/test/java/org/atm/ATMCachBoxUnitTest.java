package org.atm;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.atm.withdraw.exceptions.InsufficientAvailableMoneyException;
import org.junit.Before;
import org.junit.Test;

public class ATMCachBoxUnitTest {

	private Map<Short, Long> FULL_NOTE_AVAILABILITY;
	private Long FULL_NOTE_AVAILABILITY_TOTAL;

	@Before
	public void before() {
		Long total = 0L;
		FULL_NOTE_AVAILABILITY = new HashMap<>();
		FULL_NOTE_AVAILABILITY.put((short) 500, 2000L);
		total += 500 * 2000;
		FULL_NOTE_AVAILABILITY.put((short) 200, 4000L);
		total += 200 * 4000;
		FULL_NOTE_AVAILABILITY.put((short) 100, 4000L);
		total += 100 * 4000;
		FULL_NOTE_AVAILABILITY.put((short) 50, 3000L);
		total += 50 * 3000;
		FULL_NOTE_AVAILABILITY.put((short) 10, 500L);
		total += 10 * 500;
		FULL_NOTE_AVAILABILITY_TOTAL = total;

	}

	@Test
	public void testWithdrawAvailableAmountShouldBeWithdrawedSuccessfullyAndAvailabiltyDecreased() {
		ATMCashBox target = new ATMCashBox(FULL_NOTE_AVAILABILITY);
		Short value = 500;
		Long amount = 10L;
		target.withdraw(value, amount);
		Long expectedAvailability = 1990L;
		Long actualAvailability = target.getAvailabilityOf(value);
		assertEquals(expectedAvailability, actualAvailability);
		Long expectedTotalAvailability = FULL_NOTE_AVAILABILITY_TOTAL - 5000;
		Long actualTotalAvailability = target.getTotalAvailability();
		assertEquals(expectedTotalAvailability, actualTotalAvailability);
	}
	
	@Test(expected=InsufficientAvailableMoneyException.class)
	public void testWithdrawUnAvailableAmountShouldFailsAndThrowsInsufficientAvailableMoneyException() {
		ATMCashBox target = new ATMCashBox(FULL_NOTE_AVAILABILITY);
		Short value = 500;
		Long amount = 3000L;
		target.withdraw(value, amount);
	}
	
	@Test(expected=NullPointerException.class)
	public void testWithdrawUnExistNoteOrCoinShouldFailsAndThrowsNullPointerException() {
		ATMCashBox target = new ATMCashBox(FULL_NOTE_AVAILABILITY);
		Short value = 477;
		Long amount = 30L;
		target.withdraw(value, amount);
	}
	
	@Test
	public void testDepositeExistNoteOrCoinShouldBeSuccessAndAvailbilityIncreased() {
		ATMCashBox target = new ATMCashBox(FULL_NOTE_AVAILABILITY);
		Short value = 500;
		Long amount = 10L;
		target.deposite(value, amount);
		Long expectedAvailability = 2010L;
		Long actualAvailability = target.getAvailabilityOf(value);
		assertEquals(expectedAvailability, actualAvailability);
		Long expectedTotalAvailability = FULL_NOTE_AVAILABILITY_TOTAL + 5000;
		Long actualTotalAvailability = target.getTotalAvailability();
		assertEquals(expectedTotalAvailability, actualTotalAvailability);
	}
}
