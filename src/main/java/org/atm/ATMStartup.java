package org.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atm.integration.banking.BankingService;
import org.atm.integration.banking.IBankingService;
import org.atm.withdraw.ATMWithdrawService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ATMStartup {

	@Bean("rackService")
	public ATMCashBoxesRackService initRackService() {
		List<ATMCashBox> notesBoxes = new ArrayList<>();
		List<ATMCashBox> coinsBoxes = new ArrayList<>();

		Map<Short, Long> FULL_NOTES_AVAILABILITY = new HashMap<>();
		FULL_NOTES_AVAILABILITY.put((short) 1000, 200L);
		FULL_NOTES_AVAILABILITY.put((short) 500, 200L);
		FULL_NOTES_AVAILABILITY.put((short) 200, 200L);
		FULL_NOTES_AVAILABILITY.put((short) 100, 200L);
		FULL_NOTES_AVAILABILITY.put((short) 50, 200L);
		ATMCashBox noteBox = new ATMCashBox(FULL_NOTES_AVAILABILITY);
		notesBoxes.add(noteBox);

		Map<Short, Long> FULL_COINS1_AVAILABILITY = new HashMap<>();
		FULL_COINS1_AVAILABILITY.put((short) 1, 200L);
		FULL_COINS1_AVAILABILITY.put((short) 10, 200L);
		ATMCashBox coinBox1 = new ATMCashBox(FULL_COINS1_AVAILABILITY);
		coinsBoxes.add(coinBox1);

		Map<Short, Long> FULL_COINS2_AVAILABILITY = new HashMap<>();
		FULL_COINS2_AVAILABILITY.put((short) 5, 200L);
		FULL_COINS2_AVAILABILITY.put((short) 2, 200L);
		FULL_COINS2_AVAILABILITY.put((short) 20, 200L);
		ATMCashBox coinBox2 = new ATMCashBox(FULL_COINS2_AVAILABILITY);
		coinsBoxes.add(coinBox2);

		return new ATMCashBoxesRackService(notesBoxes, coinsBoxes);
	}

	@Bean("stateService")
	@DependsOn(value = { "rackService" })
	public ATMStateService initStateService(ATMCashBoxesRackService rackService) {
		Long totalAvailability = rackService.getTotalAvailability();
		ATMStateService stateService = ATMStateService.getInstance();
		stateService.setTotalAvailabiltiy(totalAvailability);
		return stateService;
	}

	@Bean("bankingService")
	public IBankingService initBankingService() {
		return new BankingService();
	}

	@Bean("withdrawService")
	@DependsOn(value={"bankingService", "stateService", "rackService"})
	public ATMWithdrawService initWithDrawService(IBankingService bankingService, ATMStateService stateService, ATMCashBoxesRackService rackService) {
		return new ATMWithdrawService(bankingService, stateService, rackService);
	}
	
	@Bean("atmService")
	@DependsOn(value={"bankingService", "stateService", "rackService", "withdrawService"})
	public ATMService startATM(IBankingService bankingService, ATMStateService stateService, ATMCashBoxesRackService rackService, ATMWithdrawService withdrawService) {
		return new ATMService(stateService, rackService, withdrawService, bankingService);
	}

}
