package com.banking.dashboard.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "accounts")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	@Column(name = "account_number", nullable = false, unique = true)
	private String accountNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType accountType;
	
	@Column(name = "balance", nullable = false, precision = 15, scale = 2)
	private BigDecimal balance;
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;
	
	public Account() {
	}
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	enum AccountType {
		CHECKING,
		SAVINGS,
		BUSINESS,
		INVESTMENT
	}

}
