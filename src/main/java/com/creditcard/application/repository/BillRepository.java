package com.creditcard.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditcard.application.model.Bill;


public interface BillRepository extends JpaRepository <Bill, Long>{

	List<Bill> findByCreditCardId(Long creditCardId);
	
}
