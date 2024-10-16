package com.musungare.BackendForReact.CustomerController;

import com.musungare.BackendForReact.CustomerService.CustomerService;
import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/top-up") // Change this to POST
    public void topUpAccount(@RequestParam long accountNumber,
                             @RequestParam Double amount,
                             @RequestParam Long phoneNumber) {
        // Call the service method to process the top-up
        customerService.TopUp(accountNumber, amount, phoneNumber);
        System.out.println(amount);
    }

    @GetMapping("/success")
    public String paymentSuccess() {
        // Handle successful payment
        return "Payment successful!";
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        // Handle canceled payment
        return "Payment canceled!";
    }
}
