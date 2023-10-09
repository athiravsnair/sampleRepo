package com.creditcard.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.creditcard.application.model.Customer;
import com.creditcard.application.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
    }

    public Customer registerCustomer(Customer customerDTO) {
    	customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        return customerRepository.save(customerDTO);
    }

	public void saveToken(String username, String token) {
		Customer dbCust = customerRepository.findByUsername(username);
		dbCust.setToken(token);
		customerRepository.save(dbCust);
		
	}

}

