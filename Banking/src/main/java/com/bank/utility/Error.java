package com.bank.utility;

import org.springframework.http.HttpStatus;

public class Error {

	private String msg;
	public Error(String msg) {
		super();
		this.msg = msg;
		
	}
	public Error() {
		// TODO Auto-generated constructor stub
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
