package com.musungare.BackendForReact.CustomerController;

import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerService.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
//@CrossOrigin(origins = "http://localhost:3000")  // Adjust this to match your frontend URL
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET request to fetch customer details by account number
    @GetMapping("/{email}")
    public ResponseEntity<Customer> getCustomerByAccountNumber(@PathVariable String email) {
        Customer customer = customerService.getCustomers(email);
        System.out.println("Endpoint done");
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST request to top up account
    @PostMapping("/top-up")
    public ResponseEntity<String> topUpAccount(@RequestParam String email,
                                               @RequestParam Double amount,
                                               @RequestParam Long phoneNumber) {
        customerService.TopUp(email, amount, phoneNumber);
        return ResponseEntity.ok("Top-up successful!");
    }

    // GET request to handle successful payment
    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess() {
        return ResponseEntity.ok("Payment successful!");
    }

    // GET request to handle canceled payment
    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment canceled!");
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Customer customer) {
        // Check if the customer already exists using email
        Optional<Customer> customerExists = Optional.ofNullable(customerService.getCustomers(customer.getEmail()));

        if (customerExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with this email already exists");
        }

        // If the customer doesn't exist, proceed to create the account
        customerService.AddCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully");
    }

}
