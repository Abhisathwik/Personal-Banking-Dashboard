package com.banking.dashboard.repository;

import com.banking.dashboard.entity.Account;
import com.banking.dashboard.entity.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find account by account number
    Optional<Account> findByAccountNumber(String accountNumber);
    
    boolean existsByAccountNumber(String accountNumber);
    
    // Find accounts by customer ID
    List<Account> findByCustomerCustomerId(Long customerId);
    
    // Find active accounts by customer ID
    @Query("SELECT a FROM Account a WHERE a.customer.customerId = :customerId AND a.isActive = true")
    List<Account> findActiveAccountsByCustomerId(@Param("customerId") Long customerId);
    
    // Find accounts by type
    List<Account> findByAccountType(AccountType accountType);
    
    // Find active accounts by type
    List<Account> findByAccountTypeAndIsActive(AccountType accountType, Boolean isActive);
    
    // Find accounts with balance greater than a specified amount
    @Query("SELECT a FROM Account a WHERE a.balance >= :minBalance AND a.isActive = true")
    List<Account> findAccountsWithMinimumBalance(@Param("minBalance") BigDecimal minBalance);
    
    // Get total balance for a customer
    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM Account a WHERE a.customer.customerId = :customerId AND a.isActive = true")
    BigDecimal getTotalBalanceByCustomerId(@Param("customerId") Long customerId);
    
 	// Find accounts within a balance range
    @Query("SELECT a FROM Account a WHERE a.balance BETWEEN :minBalance AND :maxBalance AND a.isActive = true")
    List<Account> findAccountsByBalanceRange(@Param("minBalance") BigDecimal minBalance, 
                                           @Param("maxBalance") BigDecimal maxBalance);
    
    // Find accounts created between dates
    List<Account> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find inactive accounts (for cleanup/review)
    List<Account> findByIsActiveFalse();
    
    // Count accounts by type
    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountType = :accountType AND a.isActive = true")
    long countByAccountType(@Param("accountType") AccountType accountType);
    
    // Find accounts with zero balance
    @Query("SELECT a FROM Account a WHERE a.balance = 0 AND a.isActive = true")
    List<Account> findAccountsWithZeroBalance();
    
    // Paginated account search
    Page<Account> findByIsActive(Boolean isActive, Pageable pageable);
}
