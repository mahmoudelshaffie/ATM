package org.atm.withdraw;

import org.atm.ATMCashBoxesRackService;
import org.atm.ATMStateService;
import org.atm.integration.banking.IBankingService;

public class ATMWithdrawService implements IWithdrawTransactionService {

	private final boolean FAILED_TX = false;
	private IBankingService bankingService;
	private ATMStateService stateService;
	private ATMCashBoxesRackService rackService;
		
	
	public ATMWithdrawService(IBankingService bankingService, ATMStateService stateService,
			ATMCashBoxesRackService rackService) {
		this.bankingService = bankingService;
		this.stateService = stateService;
		this.rackService = rackService;
	}
	

	@Override
	public void doWithdrawTransaction(CashWithdrawTransaction tx) {
		long amount = tx.getTxAmount();
		this.bankingService.checkBalance(tx.getAccountNo(), tx.getPin(), amount);
		this.stateService.withdraw(amount);
		rackService.doWithdrawTransaction(tx);
		
		if (this.bankingService.withdrawFromATM(tx.getAccountNo(), tx.getPin(), amount) == FAILED_TX) {
			this.stateService.deposite(amount);
			this.rackService.undoWithdrawTransaction(tx);
		}
	}

	@Override
	public void undoWithdrawTransaction(CashWithdrawTransaction tx) {
		this.bankingService.depositeFromATM(tx.getAccountNo(), tx.getPin(), tx.getTxAmount());
		this.stateService.deposite(tx.getTxAmount());
		this.rackService.undoWithdrawTransaction(tx);
	}

}
