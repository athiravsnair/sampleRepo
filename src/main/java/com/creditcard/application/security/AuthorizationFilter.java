package com.creditcard.application.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.creditcard.application.model.Customer;
import com.creditcard.application.model.Roles;
import com.creditcard.application.repository.CustomerRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends OncePerRequestFilter {
	private CustomerRepository customerRepository;

	public AuthorizationFilter(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String tokenHeader = request.getHeader("Authorization");
		if (tokenHeader == null || !tokenHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(tokenHeader);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
		String token = tokenHeader.split(" ")[1];
		
		Customer customer = customerRepository.findByToken(token);
		if (customer != null) { // checking customer is present for token given
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for (Roles role : customer.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}
			return new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),
					grantedAuthorities);
		} else
			return null;

	}

}
