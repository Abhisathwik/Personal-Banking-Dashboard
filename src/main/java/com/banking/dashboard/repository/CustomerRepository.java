package com.banking.dashboard.repository;

import com.banking.dashboard.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// Find a customer by their email
    Optional<Customer> findByEmail(String email);

    // Check if email already exists (for uniqueness validation)
    boolean existsByEmail(String email);	

}
