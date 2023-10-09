package com.creditcard.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.creditcard.application.model.Bill;
import com.creditcard.application.model.CreditCard;
import com.creditcard.application.repository.BillRepository;
import com.creditcard.application.repository.CreditCardRepository;

@Service
public class BillService {
    private final BillRepository billRepository;
    private final CreditCardRepository creditCardRepository;

    public BillService(BillRepository billRepository, CreditCardRepository creditCardRepository) {
        this.billRepository = billRepository;
        this.creditCardRepository = creditCardRepository;
    }

    public List<Bill> getBillsByCreditCard(Long creditCardId) {
        // Logic to retrieve bills associated with a credit card
        return billRepository.findByCreditCardId(creditCardId);
    }

    public Bill getBillDetails(Long billId) {
        // Logic to retrieve bill details by ID
        return billRepository.findById(billId).orElseThrow(() -> new RuntimeException("Bill id " + billId +" not present"));
    }

    public boolean makeBillPayment(Long billId) {
        // Logic to make a bill payment
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new RuntimeException("Bill id " + billId +" not present"));
        if (!bill.isPaid()) {
            bill.setPaid(true);
            billRepository.save(bill);
            return true;
        }
        return false;
    }

	public Bill generateBill(Long creditCardId, Bill bill) {
		CreditCard creditCard = creditCardRepository.findById(creditCardId).orElseThrow(() -> new RuntimeException("creditCardId " + creditCardId + " not present"));

		Bill newBill = new Bill();
		newBill.setAmount(bill.getAmount());
		newBill.setCreditCard(creditCard);
		newBill.setPaid(false);
		newBill.setBillDate(bill.getBillDate());

		// Implement additional bill generation logic as needed
		return billRepository.save(newBill);
	}
}
