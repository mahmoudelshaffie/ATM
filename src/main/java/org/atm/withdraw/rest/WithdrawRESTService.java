package org.atm.withdraw.rest;

import org.atm.ATMService;
import org.atm.withdraw.CashWithdrawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/withdraw")
public class WithdrawRESTService {

	@Autowired
	@Qualifier("atmService")
	private ATMService atmService;
	
	public WithdrawRESTService() {
	}
	
	@PutMapping
	public @ResponseBody CashWithdrawl withdraw(@RequestParam Long amount, @RequestHeader(name="X-ACC") Long accountNo, @RequestHeader(name="X-PIN") Short pin) {
		return atmService.withdraw(accountNo, pin, amount);
	}
}
