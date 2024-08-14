package com.bank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {

//	public Optional<Account> findByaccountNo(Long accountNo);

}
