package com.banking.dashboard.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	@Column(name = "transaction_number", nullable = false, unique = true)
	private String transactionNumber;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(name = "amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal amount;
	
	@Column(name = "transaction_date", nullable = false)
	private LocalDateTime transactionDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destination_account_id")
	private Account destinationAccount; // For transfers
	
	public Transaction() {
	}
	
	public Long getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getTransactionNumber() {
		return transactionNumber;
	}
	
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	enum TransactionType {
		DEPOSIT, WITHDRAWAL, TRANSFER , PAYMENT, REFUND
	}
}
