package com.creditcard.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditcard.application.model.Customer;


public interface CustomerRepository extends JpaRepository <Customer, Long>{

	Customer findByUsername(String username);
	
	Customer findByToken(String token);
	
	
}
