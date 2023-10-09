package com.creditcard.application.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String token;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_has_roles",
        joinColumns = {
        		@JoinColumn(name = "users_id", referencedColumnName = "id")
        		},
        inverseJoinColumns = {
        		@JoinColumn(name = "roles_id", referencedColumnName  = "id")
        		}
        )
    private Set<Roles> roles = new LinkedHashSet<Roles>();
	
	
	public Customer(String username, String password, Set<Roles> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}


}
