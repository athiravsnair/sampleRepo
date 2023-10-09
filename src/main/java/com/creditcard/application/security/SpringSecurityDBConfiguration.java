package com.creditcard.application.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.creditcard.application.repository.CustomerRepository;

@Configuration
public class SpringSecurityDBConfiguration {
	
	private final CustomerRepository customerRepository;

	public SpringSecurityDBConfiguration(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
    }
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {web.ignoring().requestMatchers(antMatcher("/customers/register"));
        	    		 web.ignoring().requestMatchers(antMatcher("/customers/login"));
        	    		};
    }

	// Authorization : 1) The user should be logged in to access particular api.
	//                 2) The api which user wants to access , is he allowed/authorize or not ?
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> {
			//authorize.requestMatchers(antMatcher("/creditcards/**")).hasAuthority("USER"); // To do credit-card operations only USER can do it.
			//authorize.requestMatchers(antMatcher("/bills/**")).hasAuthority("USER"); // USER can generate/view /bills/** (POST,GET...)
			authorize.anyRequest().authenticated();
		}).httpBasic(Customizer.withDefaults())
		.csrf(x -> x.disable())
		.addFilterBefore(new AuthorizationFilter(customerRepository), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
