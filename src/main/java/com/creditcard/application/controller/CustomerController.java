package com.creditcard.application.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.application.model.Customer;
import com.creditcard.application.request.LoginRequest;
import com.creditcard.application.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomerController(CustomerService customerService, AuthenticationManager authenticationManager) {
        this.customerService = customerService;
		this.authenticationManager = authenticationManager;
    }

    // Register a new customer
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customerDTO) {
        Customer newCustomer = customerService.registerCustomer(customerDTO);
        return ResponseEntity.ok(newCustomer);
    }
    
    @PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {

    	//after below call , flows goes to UserManagemetnService class
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		//flows comes back after doing authentication from UserManagementService.
		if (authentication.isAuthenticated()) {
			String token = UUID.randomUUID().toString();
			customerService.saveToken(loginRequest.getUsername(), token);
			return ResponseEntity.ok().header("token", token).body("Login succesfull!!");
		} else
			return ResponseEntity.badRequest().body("Invalid credentials");
	}
}

