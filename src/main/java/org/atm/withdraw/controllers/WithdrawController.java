package org.atm.withdraw.controllers;

import java.util.Map;

import org.atm.ATMService;
import org.atm.withdraw.CashWithdrawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WithdrawController {
	
	@Autowired
	private ATMService atmService;

	@GetMapping("/ui/cash-withdraw")
	public String welcome(Map<String, Object> model) {
		model.put("withdrawRequest", new CashWithdrawRequest());
		return "withdraw";
	}
	
	@PostMapping("/withdraw")
	public String withdrawSubmit(@ModelAttribute CashWithdrawRequest withdrawRequest, Map<String, Object> model) {
		Long accountNo = new Long(withdrawRequest.getAccountNo());
		Short pin = new Short(withdrawRequest.getPin());
		Long amount = new Long(withdrawRequest.getAmount());
		try {
			CashWithdrawl cash = atmService.withdraw(accountNo, pin, amount);
			model.put("cash", cash);
			return "success-tx";			
		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "failed-tx";
		}
	}
}
