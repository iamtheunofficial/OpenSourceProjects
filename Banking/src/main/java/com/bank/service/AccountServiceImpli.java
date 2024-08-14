package com.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dto.AccountStatus;
import com.bank.dto.CustomerDto;
import com.bank.exception.AccountExists;
import com.bank.exception.AccountNotFound;
import com.bank.dto.AccountDTo;
import com.bank.model.Account;
import com.bank.model.AccountType;
import com.bank.model.Customer;
import com.bank.repo.AccountRepo;
import com.bank.repo.CustomerRepo;

@Service
public class AccountServiceImpli implements IAccountService {

	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
	public AccountStatus addAccount(Account account) {
		Account response=null;
		Customer saved=null;

		Customer customer=account.getCustomer();
		 Optional<Customer> fromDb=customerRepo.findByEmail(customer.getEmail());
		 if(fromDb.isPresent()) {
			 if(!fromDb.get().getAccounts().containsKey(account.getAccountType())) {
			 account.setCustomer(fromDb.get());
			 response=accountRepo.save(account);
fromDb.get().getAccounts().put(account.getAccountType(), response);
customerRepo.save(fromDb.get());

			saved=customerRepo.save(fromDb.get());
		 }else{
			 throw new AccountExists("Äccount  already exists");
			 }
		 }else {
		 
		 saved=customerRepo.save(customer);
Map<AccountType,Account> accountTypes=new HashMap<AccountType, Account>();
accountTypes.put(account.getAccountType(), account);
		customer.setAccounts(accountTypes);
		 response=accountRepo.save(account);

		account.setCustomer(saved);
		
		
	}
			return 	new AccountStatus(account.getAccountType()+" account created successfully",response.getAccountId(),saved.getCustomerId());

	}
	
	@Override
	public List<AccountDTo> getAllAccounts() {
List<Account> accounts=accountRepo.findAll();

List<AccountDTo> dto = new ArrayList<>();
for (Account k : accounts) {
	dto.add(mapper.map(k, AccountDTo.class));
}
return dto;	}
	
	@Override
	public AccountDTo getAccount(Long accountNo) {
Optional<Account> optional=accountRepo.findById(accountNo);
AccountDTo dto=null;
if(optional.isPresent()) {
	dto=mapper.map(optional.get(), AccountDTo.class);
	dto.setCustomerEmail(optional.get().getCustomer().getEmail());
	dto.setCustomerName(optional.get().getCustomer().getName());
	return dto;

}
throw new AccountNotFound("INVALID ACCOUNT NUMBER");

	}
	@Override
	public String transfer(Long fromAcc, Long toAcc, Double amount) {
Optional<Account> fromaccount=accountRepo.findById(fromAcc);
Optional<Account> toaccount=accountRepo.findById(toAcc);
if(fromaccount.isPresent() && toaccount.isPresent()) {
	Account from=fromaccount.get();
	Account to=toaccount.get();
	if(from.getBalance()>=amount) {
		to.setBalance(to.getBalance()+amount);
		from.setBalance(from.getBalance()-amount);
		accountRepo.saveAll(List.of(from,to));
		return "amount "+amount +"tranferes from account no ="+fromAcc +"to"+toAcc+"succesfulluy";
	}else {
throw new RuntimeException("Insufficient Balance");
	}
}else {
throw new AccountNotFound("Inavlid account Number Please check account Numbers");
}
	}
	
	
}
