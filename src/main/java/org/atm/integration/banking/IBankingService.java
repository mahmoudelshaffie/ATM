package org.atm.integration.banking;

import org.atm.withdraw.exceptions.InsufficientBalanceException;

public interface IBankingService {
	void checkBalance(long accountNo, short pin, long amount) throws InsufficientBalanceException;
	boolean withdrawFromATM(long accountNo, short pin, long amount);
	void depositeFromATM(long accountNo, short pin, long amount);
}
