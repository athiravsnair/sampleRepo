package com.creditcard.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.application.model.CreditCard;
import com.creditcard.application.service.CreditCardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/creditcards")
public class CreditCardController {
    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    // Create a new credit card for a customer
    @PostMapping("/{customerId}")
    public ResponseEntity<CreditCard> createCreditCard(@PathVariable Long customerId,@Valid @RequestBody CreditCard creditCard) {
        CreditCard newCreditCard = creditCardService.createCreditCard(customerId, creditCard);
        return ResponseEntity.ok(newCreditCard);
    }

    // Update an existing credit card
    @PutMapping("/{creditCardId}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable Long creditCardId, @Valid @RequestBody CreditCard updatedCreditCard) {
        CreditCard updatedCard = creditCardService.updateCreditCard(creditCardId, updatedCreditCard);
        return ResponseEntity.ok(updatedCard);
    }

    // Delete an existing credit card
    @DeleteMapping("/{creditCardId}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long creditCardId) {
        creditCardService.deleteCreditCard(creditCardId);
        return ResponseEntity.noContent().build();
    }

}

