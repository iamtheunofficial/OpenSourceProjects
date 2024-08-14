package com.bank.dto;

import java.util.List;

import com.bank.model.Account;
import com.bank.model.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

public class AccountDTo {
	private Long accountId;
//	private Long accountNo;
	private String accountType;
	private Double balance;
    private String customerName;
    private String customerEmail;

	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
//	public Long getAccountNo() {
//		return accountNo;
//	}
//	public void setAccountNo(Long accountNo) {
//		this.accountNo = accountNo;
//	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "AccountDTo [accountId=" + accountId + ", accountType=" + accountType + ", balance=" + balance
				+ ", customerName=" + customerName + ", customerEmail=" + customerEmail + "]";
	}
	
	
	
}
