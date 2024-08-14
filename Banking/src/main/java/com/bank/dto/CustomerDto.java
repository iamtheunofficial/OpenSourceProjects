package com.bank.dto;

import java.time.LocalDate;

import com.bank.model.Account;

public class CustomerDto {

//	private Long customerId;
	private String name;
	private String email;
	private String address;
	private LocalDate dateOfBirth;
	private Long age;
	private Long mblNo;
//    private AccountDTo account;
//	public Long getCustomerId() {
//		return customerId;
//	}
//	public void setCustomerId(Long customerId) {
//		this.customerId = customerId;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getMblNo() {
		return mblNo;
	}
	public void setMblNo(Long mblNo) {
		this.mblNo = mblNo;
	}
//	public AccountDTo getAccount() {
//		return account;
//	}
//	public void setAccount(AccountDTo account) {
//		this.account = account;
//	}
    
    

}
