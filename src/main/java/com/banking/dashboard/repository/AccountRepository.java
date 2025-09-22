package com.banking.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.dashboard.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	

	// Find account by unique account number
    Optional<Account> findByAccountNumber(String accountNumber);

    // List all accounts belonging to a particular customer ID
    List<Account> findByCustomerCustomerId(Long customerId);
	

}
