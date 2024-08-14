package com.bank.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.AccountStatus;
import com.bank.dto.CustomerDto;
import com.bank.dto.Transfer;
import com.bank.dto.AccountDTo;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.service.IAccountService;
import com.bank.service.ICustomerService;

import jakarta.websocket.server.PathParam;

@RestController
public class MainController {

	@Autowired
	IAccountService accountService;

	@Autowired
	ICustomerService customerService;

	@PostMapping("/addaccount")
	public ResponseEntity<AccountStatus> addAccount(@RequestBody Account acc) {
		AccountStatus response = accountService.addAccount(acc);
		return new ResponseEntity<AccountStatus>(response, HttpStatus.CREATED);
	}

	@GetMapping("/getallaccounts")
	public ResponseEntity<?> getAllAccoounts() {
		List<AccountDTo> accounts = accountService.getAllAccounts();
		return new ResponseEntity<List<AccountDTo>>(accounts, HttpStatus.OK);
	}

	@GetMapping("/getallcustomers")
	public ResponseEntity<List<CustomerDto>> getAllCustomer() {

		List<CustomerDto> customers = customerService.getAllCustomer();
		return new ResponseEntity<List<CustomerDto>>(customers, HttpStatus.OK);
	}

	@GetMapping("/getaccountbyno/{accno}")
	public ResponseEntity<AccountDTo> getAccountByNo(@PathVariable Long accno) {
		AccountDTo account = accountService.getAccount(accno);
		return new ResponseEntity<AccountDTo>(account, HttpStatus.OK);

	}

	@PostMapping("/transfer")
	public ResponseEntity<?> transferFund(@RequestBody Transfer transfer) {
		String responc = accountService.transfer(transfer.getFrom(), transfer.getTo(), transfer.getAmount());
		return new ResponseEntity<String>(responc, HttpStatus.OK);

	}

	@PutMapping("/updatecustomer/{id}/{email}")
	public ResponseEntity<?> updateCustome(@RequestBody CustomerDto dto, @PathVariable Long id,
			@PathVariable String email) {

		return new ResponseEntity<CustomerDto>(customerService.update(dto, id, email), HttpStatus.OK);
	}
}
