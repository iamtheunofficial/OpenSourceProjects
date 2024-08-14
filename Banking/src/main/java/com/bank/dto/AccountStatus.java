package com.bank.dto;

public class AccountStatus {

	private String msg;
	private Long accountNo;
	private Long customerId;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public AccountStatus(String msg, Long accountNo) {
		super();
		this.msg = msg;
		this.accountNo = accountNo;
	}
	public AccountStatus(String msg, Long accountNo, Long customerId) {
		super();
		this.msg = msg;
		this.accountNo = accountNo;
		this.customerId = customerId;
	}
	public AccountStatus() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AccountStatus [msg=" + msg + ", accountNo=" + accountNo + ", customerId=" + customerId + "]";
	}
	
}
