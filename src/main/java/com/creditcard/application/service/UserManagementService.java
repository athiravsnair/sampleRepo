package com.creditcard.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.creditcard.application.model.Roles;
import com.creditcard.application.model.Customer;
import com.creditcard.application.repository.CustomerRepository;

@Component
public class UserManagementService implements UserDetailsService {

	private CustomerRepository customerRepository;

	public UserManagementService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Customer customer = customerRepository.findByUsername(username);
		if (customer == null) {
			throw new UsernameNotFoundException("User not found:" + username);
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Roles role : customer.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(),
				grantedAuthorities);
	}
}
