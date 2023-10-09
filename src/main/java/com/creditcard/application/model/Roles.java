package com.creditcard.application.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Data
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

    @Column
    private String name;

	public Roles(String name) {
		this.name = name;
	}

	public Roles() {
		super();
	}
    
}
