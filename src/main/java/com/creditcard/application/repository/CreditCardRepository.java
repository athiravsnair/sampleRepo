package com.creditcard.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditcard.application.model.CreditCard;


public interface CreditCardRepository extends JpaRepository <CreditCard, Long>{

}
