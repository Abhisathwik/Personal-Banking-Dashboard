package com.banking.dashboard.repository;

import com.banking.dashboard.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// Find a customer by their email
    Optional<Customer> findByEmail(String email);

    // Check if email already exists (for uniqueness validation)
    boolean existsByEmail(String email);	
    
    // Search customers by name (first or last) with case-insensitive partial matching
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Fetch customers with their accounts using pagination
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.accounts")
    Page<Customer> findCustomersWithAccounts(Pageable pageable);
    
    // Find customers by phone number
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    
    // Find customers created between specific dates
    List<Customer> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Count total number of customers
    @Query("SELECT COUNT(c) FROM Customer c")
    long countTotalCustomers();
    
    // Find customers by email domain (e.g., all customers with emails from "gmail.com")
    @Query("SELECT c FROM Customer c WHERE c.email LIKE CONCAT('%@', :domain)")
    List<Customer> findbyEmailDomain(@Param("domain") String domain);

}
