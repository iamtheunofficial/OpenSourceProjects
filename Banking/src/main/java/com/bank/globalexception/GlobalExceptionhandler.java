package com.bank.globalexception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bank.exception.AccountExists;
import com.bank.exception.AccountNotFound;
import com.bank.utility.Error;
@RestControllerAdvice
public class GlobalExceptionhandler {

	@ExceptionHandler(AccountExists.class)
	public ResponseEntity<Error> AccountExists(AccountExists exists){
	Error er=new Error(exists.getMessage());
	return new ResponseEntity<Error>(er,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccountNotFound.class)
	public ResponseEntity<Error> AccountExists(AccountNotFound exists){
	Error er=new Error(exists.getMessage());
	return new ResponseEntity<Error>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> AccountExists(Exception exists){
	Error er=new Error(exists.getMessage());
	return new ResponseEntity<Error>(er,HttpStatus.NOT_FOUND);
	}
	
}

