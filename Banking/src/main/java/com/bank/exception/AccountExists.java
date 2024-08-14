package com.bank.exception;

public class AccountExists extends RuntimeException {

	public AccountExists(String msg) {
super(msg);
	}
}
