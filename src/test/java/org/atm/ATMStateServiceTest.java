package org.atm;

import static org.junit.Assert.assertEquals;

import org.atm.withdraw.exceptions.InsufficientAvailableMoneyException;
import org.junit.Test;

public class ATMStateServiceTest {

	private final Long TOTAL_AVAILABILITY = 2000L; 
	
	@Test
	public void testWithDrawAvailableAmountShouldBeWithdrawedSuccessfully() {
		ATMStateService target = ATMStateService.getInstance();
		target.setTotalAvailabiltiy(TOTAL_AVAILABILITY);
		Long amount = 500L;
		target.withdraw(amount);
		Long actual = target.getTotalAvailabiltiy();
		long expectedAvailability = 1500L;
		assertEquals(expectedAvailability, actual, 0);
	}
	
	@Test(expected=InsufficientAvailableMoneyException.class)
	public void testWithDrawUnAvailableAmountShouldFailAndThrowsInsufficientAvailableMoneyException() {
		ATMStateService target = ATMStateService.getInstance();
		target.setTotalAvailabiltiy(TOTAL_AVAILABILITY);
		Long amount = 2500L;
		target.withdraw(amount);
		Long actual = target.getTotalAvailabiltiy();
		long expectedAvailability = 1500L;
		assertEquals(expectedAvailability, actual, 0);
	}
	
	@Test
	public void testDepositeShouldTotalAvailabilityBeIncreasedSuccessfully() {
		ATMStateService target = ATMStateService.getInstance();
		target.setTotalAvailabiltiy(TOTAL_AVAILABILITY);
		Long amount = 500L;
		target.deposite(amount);
		Long actual = target.getTotalAvailabiltiy();
		long expectedAvailability = 2500L;
		assertEquals(expectedAvailability, actual, 0);
	}
}
