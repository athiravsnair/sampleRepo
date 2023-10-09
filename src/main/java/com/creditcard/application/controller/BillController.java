package com.creditcard.application.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.application.model.Bill;
import com.creditcard.application.service.BillService;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }
    
    // Generate a new bill for a specific credit card
    @PostMapping("/generate/{creditCardId}")
	public ResponseEntity<String> generateBill(@PathVariable Long creditCardId, @RequestBody Bill bill) {
		billService.generateBill(creditCardId, bill);
		return ResponseEntity.ok("Bill generation successful");
	}

    // Get a list of all bills for a specific credit card
    @GetMapping("/creditcard/{creditCardId}")
    public ResponseEntity<List<Bill>> getBillsByCreditCard(@PathVariable Long creditCardId) {
        List<Bill> bills = billService.getBillsByCreditCard(creditCardId);
        return ResponseEntity.ok(bills);
    }

    // Get details of a specific bill
    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBillDetails(@PathVariable Long billId) {
        Bill bill = billService.getBillDetails(billId);
        return ResponseEntity.ok(bill);
    }

    // Make a payment for a specific bill
    @PostMapping("/{billId}/pay")
    public ResponseEntity<String> makeBillPayment(@PathVariable Long billId) {
        boolean paymentSuccessful = billService.makeBillPayment(billId);

        if (paymentSuccessful) {
            return ResponseEntity.ok("Payment successful");
        } else {
            return ResponseEntity.ok("Payment already done");
        }
    }
}
