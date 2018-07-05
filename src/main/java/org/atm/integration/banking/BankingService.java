package org.atm.integration.banking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.atm.withdraw.exceptions.InsufficientBalanceException;

public class BankingService implements IBankingService {

	private Map<Long, Long> accounts;

	public BankingService() {
		accounts = new ConcurrentHashMap<>();
		accounts.put(123451L, 1000L);
		accounts.put(123452L, 2000L);
		accounts.put(123453L, 3000L);
		accounts.put(123454L, 4000L);
		accounts.put(123455L, 5000L);
	}

	@Override
	public void checkBalance(long accNo, short pin, long amount) {
		double balance = this.getBalance(accNo, pin);
		if (amount > balance) {
			throw new InsufficientBalanceException(balance, amount);
		}
	}

	public double getBalance(Long accNo, short pin) {
		return accounts.get(accNo);
	}

	@Override
	public boolean withdrawFromATM(long accountNo, short pin, long amount) {
		boolean success = false;
		Long balance = accounts.get(accountNo);
		if (balance != null) {
			if (amount <= balance) {
				accounts.put(accountNo, balance - amount);
				success = true;
			}
		}
		return success;
	}

	@Override
	public void depositeFromATM(long accountNo, short pin, long amount) {
		Long balance = accounts.get(accountNo);
		if (balance != null) {
			accounts.put(accountNo, balance + amount);
		}
	}
}
