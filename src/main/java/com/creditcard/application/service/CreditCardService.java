package com.creditcard.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditcard.application.model.CreditCard;
import com.creditcard.application.model.Customer;
import com.creditcard.application.repository.CreditCardRepository;
import com.creditcard.application.repository.CustomerRepository;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository, CustomerRepository customerRepository) {
        this.creditCardRepository = creditCardRepository;
		this.customerRepository = customerRepository;
    }

    public CreditCard createCreditCard(Long customerId, CreditCard creditCard) {
        // Implement credit card creation logic
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("customerId " + customerId + " not present"));
		creditCard.setCustomer(customer);
		// Set other properties
		return creditCardRepository.save(creditCard);

	 }

    // Update an existing credit card
    public CreditCard updateCreditCard(Long creditCardId, CreditCard updatedCreditCard) {
    	CreditCard existingCreditCard = creditCardRepository.findById(creditCardId).orElseThrow(() -> new RuntimeException("creditCardId " + creditCardId + " not present"));

		// Update the credit card details with the new values
    	existingCreditCard.setCardNumber(updatedCreditCard.getCardNumber());
	    existingCreditCard.setExpiryDate(updatedCreditCard.getExpiryDate());
		existingCreditCard.setCvv(updatedCreditCard.getCvv());
		// Update other properties as needed
        return creditCardRepository.save(existingCreditCard);
	}
    
    // Delete an existing credit card
    public void deleteCreditCard(Long creditCardId) {
    	creditCardRepository.findById(creditCardId).orElseThrow(() -> new RuntimeException("creditCardId " + creditCardId + " not present"));
        creditCardRepository.deleteById(creditCardId);
    }
}

