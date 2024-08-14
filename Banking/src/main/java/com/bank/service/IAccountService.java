package com.bank.service;

import java.util.List;

import com.bank.dto.AccountStatus;
import com.bank.dto.AccountDTo;
import com.bank.model.Account;

public interface IAccountService {

	public AccountStatus addAccount(Account account);
	
	public List<AccountDTo> getAllAccounts();
	
	public AccountDTo getAccount(Long accountNo);
	
	public String transfer(Long fromAcc,Long toAcc,Double amount);
	
}
