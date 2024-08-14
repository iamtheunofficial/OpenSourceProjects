package com.bank.service;

import java.util.List;

import com.bank.dto.CustomerDto;
import com.bank.model.Customer;

public interface ICustomerService {

	public List<CustomerDto> getAllCustomer();
	public CustomerDto update(CustomerDto customerDto,Long id,String email);

}
