package org.atm;

import org.atm.integration.banking.IBankingService;
import org.atm.withdraw.ATMWithdrawService;
import org.atm.withdraw.CashWithdrawTransaction;
import org.atm.withdraw.CashWithdrawl;

public class ATMService {

	private ATMStateService stateService;
	private ATMCashBoxesRackService rackService;
	private ATMWithdrawService withdrawService;
	private IBankingService bankingService;
	
	public ATMService(ATMStateService stateService, ATMCashBoxesRackService rackService, ATMWithdrawService withdrawService, IBankingService bankingService) {
		this.stateService = stateService;
		this.rackService = rackService;
		this.withdrawService = withdrawService;
		this.bankingService = bankingService;
	}
	
	public CashWithdrawl withdraw(long accountNo, short pin, long amount) {
		CashWithdrawTransaction tx = new CashWithdrawTransaction(amount, accountNo, pin);
		withdrawService.doWithdrawTransaction(tx);
		return tx.getCashWithdrawl();
	}
}
