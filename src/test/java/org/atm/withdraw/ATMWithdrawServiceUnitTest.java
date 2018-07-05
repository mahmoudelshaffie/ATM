package org.atm.withdraw;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.atm.ATMCashBoxesRackService;
import org.atm.ATMStateService;
import org.atm.integration.banking.IBankingService;
import org.atm.withdraw.ATMWithdrawService;
import org.atm.withdraw.CashWithdrawTransaction;
import org.junit.Test;

public class ATMWithdrawServiceUnitTest {

	private final Long ACCOUNT_NO = 123454646L;
	private final Short PIN = 1234;
	
	@Test
	public void testDoWithdrawTransactionWithAvailableBalanceAndAmountShouldBeDoneSuccessfully() {
		Long txAmount = 1000L;
		boolean SUCCESSFULL_TX = true;
		IBankingService mockedBankingService = mock(IBankingService.class);
		when(mockedBankingService.withdrawFromATM(ACCOUNT_NO, PIN, txAmount)).thenReturn(SUCCESSFULL_TX);
		ATMStateService mockedStateService = mock(ATMStateService.class);	
		ATMCashBoxesRackService mockedRackService = mock(ATMCashBoxesRackService.class);
		ATMWithdrawService target = new ATMWithdrawService(mockedBankingService, mockedStateService, mockedRackService);
		
		CashWithdrawTransaction tx = new CashWithdrawTransaction(txAmount, ACCOUNT_NO, PIN);
		target.doWithdrawTransaction(tx);
		
		verify(mockedBankingService, times(1)).checkBalance(ACCOUNT_NO, PIN, txAmount);
		verify(mockedStateService, times(1)).withdraw(txAmount);
		verify(mockedRackService, times(1)).doWithdrawTransaction(tx);
		verify(mockedBankingService, times(1)).withdrawFromATM(ACCOUNT_NO, PIN, txAmount);
		
		// Verify It is done successfully
		verify(mockedStateService, times(0)).deposite(txAmount);
		verify(mockedRackService, times(0)).undoWithdrawTransaction(tx);
	}
	
	@Test
	public void testDoWithdrawTransactionAndBankWithdrawFailedShouldUndoWithdrawTXSuccessfully() {
		Long txAmount = 1000L;
		boolean FAILED_TX = false;
		IBankingService mockedBankingService = mock(IBankingService.class);
		when(mockedBankingService.withdrawFromATM(ACCOUNT_NO, PIN, txAmount)).thenReturn(FAILED_TX);
		ATMStateService mockedStateService = mock(ATMStateService.class);	
		ATMCashBoxesRackService mockedRackService = mock(ATMCashBoxesRackService.class);
		ATMWithdrawService target = new ATMWithdrawService(mockedBankingService, mockedStateService, mockedRackService);
		CashWithdrawTransaction tx = new CashWithdrawTransaction(1000, ACCOUNT_NO, PIN);
		target.doWithdrawTransaction(tx);
		
		// Verify tx is done
		verify(mockedBankingService, times(1)).checkBalance(ACCOUNT_NO, PIN, txAmount);
		verify(mockedStateService, times(1)).withdraw(txAmount);
		verify(mockedRackService, times(1)).doWithdrawTransaction(tx);
		verify(mockedBankingService, times(1)).withdrawFromATM(ACCOUNT_NO, PIN, txAmount);
		
		// And then undone
		verify(mockedStateService, times(1)).deposite(txAmount);
		verify(mockedRackService, times(1)).undoWithdrawTransaction(tx);
	}
	
	@Test
	public void testUnDoWithdrawTransactionShouldBeUnDoneSuccessfully() {
		Long txAmount = 1000L;
		boolean SUCCESSFULL_TX = true;
		IBankingService mockedBankingService = mock(IBankingService.class);
		when(mockedBankingService.withdrawFromATM(ACCOUNT_NO, PIN, txAmount)).thenReturn(SUCCESSFULL_TX);
		ATMStateService mockedStateService = mock(ATMStateService.class);	
		ATMCashBoxesRackService mockedRackService = mock(ATMCashBoxesRackService.class);
		ATMWithdrawService target = new ATMWithdrawService(mockedBankingService, mockedStateService, mockedRackService);
		
		CashWithdrawTransaction tx = new CashWithdrawTransaction(txAmount, ACCOUNT_NO, PIN);
		target.undoWithdrawTransaction(tx);
		
		verify(mockedStateService, times(1)).deposite(txAmount);
		verify(mockedRackService, times(1)).undoWithdrawTransaction(tx);
		verify(mockedBankingService, times(1)).depositeFromATM(ACCOUNT_NO, PIN, txAmount);
	}
}

