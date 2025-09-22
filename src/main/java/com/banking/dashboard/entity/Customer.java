package com.banking.dashboard.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;


@Entity
@Table(name = "customers")
public class Customer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	
	@NotBlank(message = "First name is Required")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank(message = "Last name is Required")
	@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank(message = "Email is Required")
	@Email(message = "Email should be valid")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "phone_number", length = 15)
	@Pattern(regexp = "^\\+?[0-9. ()-]{7,15}$", message = "Phone number is invalid")
	private String phoneNumber;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Account> accounts = new ArrayList<>();
	
	public Customer() {
	}
	
	public Long getCustomerId() {
		return customerId; 
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId; 
	}

	public String getFirstName() {
		return firstName; 
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName; 
	}

	public String getLastName() {
		return lastName; 
	}
	public void setLastName(String lastName) {
		this.lastName = lastName; 
	}

	public String getEmail() {
		return email; 
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber; 
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber; 
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
		account.setCustomer(this);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
		account.setCustomer(null);
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
}
