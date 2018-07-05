package org.atm.withdraw;

public interface IWithdrawTransactionService {
	void doWithdrawTransaction(CashWithdrawTransaction tx);
	void undoWithdrawTransaction(CashWithdrawTransaction tx);
}
