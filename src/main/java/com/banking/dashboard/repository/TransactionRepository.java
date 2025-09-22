package com.banking.dashboard.repository;

import com.banking.dashboard.entity.Transaction;
import com.banking.dashboard.entity.TransactionType;
import com.banking.dashboard.entity.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find transaction by its unique transaction number
    boolean existsByTransactionNumber(String transactionNumber);
    
   // Find transactions by account with pagination and sorting by date
    Page<Transaction> findByAccountAccountIdOrderByTransactionDateDesc(Long accountId, Pageable pageable);
    
    // Find transactions by account and type
    List<Transaction> findByAccountAccountIdAndTransactionType(Long accountId, TransactionType transactionType);
    
    // Find transactions by account and status
    List<Transaction> findByAccountAccountIdAndStatus(Long accountId, TransactionStatus status);
    
    // Find transactions within a date range for an account
    @Query("SELECT t FROM Transaction t WHERE t.account.accountId = :accountId " +
           "AND t.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsByAccountAndDateRange(
            @Param("accountId") Long accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    // Find large transactions above a certain threshold
    @Query("SELECT t FROM Transaction t WHERE t.amount > :threshold " +
           "AND t.status = :status ORDER BY t.transactionDate DESC")
    List<Transaction> findLargeTransactions(
            @Param("threshold") BigDecimal threshold,
            @Param("status") TransactionStatus status);
    
    // Find failed transactions for investigation
    List<Transaction> findByStatusOrderByTransactionDateDesc(TransactionStatus status);
    
    // Find pending transactions (for processing)
    List<Transaction> findByStatusAndTransactionDateBefore(TransactionStatus status, LocalDateTime cutoffTime);
    
    // Get transaction summary by account (count and total amount by type)
    @Query("SELECT t.transactionType, COUNT(t), SUM(t.amount) " +
           "FROM Transaction t WHERE t.account.accountId = :accountId " +
           "AND t.status = 'COMPLETED' GROUP BY t.transactionType")
    List<Object[]> getTransactionSummaryByAccount(@Param("accountId") Long accountId);
    
    // Find transfers (transactions with destination accounts)
    @Query("SELECT t FROM Transaction t WHERE t.destinationAccount IS NOT NULL " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findAllTransfers();
    
    // Find transactions by customer (across all accounts)
    @Query("SELECT t FROM Transaction t WHERE t.account.customer.customerId = :customerId " +
           "ORDER BY t.transactionDate DESC")
    Page<Transaction> findTransactionsByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
    
    // Find daily transaction volume
    @Query("SELECT DATE(t.transactionDate) as date, COUNT(t), SUM(t.amount) " +
           "FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
           "AND t.status = 'COMPLETED' GROUP BY DATE(t.transactionDate) ORDER BY date DESC")
    List<Object[]> getDailyTransactionVolume(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
    
    // Count transactions by type and status
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionType = :type AND t.status = :status")
    long countByTypeAndStatus(@Param("type") TransactionType type, @Param("status") TransactionStatus status);
    
    // Find recent transactions (last N days)
    @Query("SELECT t FROM Transaction t WHERE t.transactionDate >= :since " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findRecentTransactions(@Param("since") LocalDateTime since);
}
