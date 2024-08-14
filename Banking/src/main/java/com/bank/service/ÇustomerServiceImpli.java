package com.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dto.CustomerDto;
import com.bank.model.Customer;
import com.bank.repo.CustomerRepo;

@Service
public class ÇustomerServiceImpli implements ICustomerService {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ModelMapper mapper;

	@Override
	public List<CustomerDto> getAllCustomer() {
		List<Customer> customers = customerRepo.findAll();
		List<CustomerDto> dto = new ArrayList<>();
		for (Customer k : customers) {
			dto.add(mapper.map(k, CustomerDto.class));
		}
		return dto;
	}

	@Override
	public CustomerDto update(CustomerDto customerDto, Long id, String email) {
		Optional<Customer> optional = customerRepo.findByCustomerIdAndEmail(id, email);
		if (optional.isPresent()) {
			Customer res = mapper.map(customerDto, Customer.class);
			res.setCustomerId(id);
			return mapper.map(customerRepo.save(res), CustomerDto.class);

		}
		throw new RuntimeException("Customer Not Found");
	}
}
