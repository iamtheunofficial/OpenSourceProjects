package com.bank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	public Optional<Customer> findByEmail(String email);
	public Optional<Customer> findByCustomerIdAndEmail(Long customerId, String email);

}
