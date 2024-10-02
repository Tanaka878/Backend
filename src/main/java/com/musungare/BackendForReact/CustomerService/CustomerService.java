package com.musungare.BackendForReact.CustomerService;

import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    // Fetch customer details based on account number
    public Customer getCustomers(Long accountNumber) {
        return customerRepo.findCustomerByAccountNumber(accountNumber);
    }

    // Add a new customer with a unique account number
    public void AddCustomer(Customer customer) {
        Random account = new Random();
        long random = account.nextInt(90000) + 10000;

        Optional<Customer> optionalAccount = Optional.ofNullable(customerRepo.findCustomerByAccountNumber(random));
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepo.findCustomerByEmail(customer.getEmail()));

        while (optionalAccount.isPresent()) {
            random = account.nextInt(90000) + 10000;
            optionalAccount = Optional.ofNullable(customerRepo.findCustomerByAccountNumber(random));
        }

        if (customerOptional.isPresent()) {
            throw new RuntimeException("Customer already exists");
        } else {
            customer.setAccountNumber(random);
            customerRepo.save(customer);
        }
    }

    // Update customer details (like balance)
    @Transactional
    public void UpdateCustomerDetails(Long accountNumber, Double balance) {
        Customer customer = customerRepo.findCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            customer.setBalance(customer.getBalance() - balance);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    // Top up customer account by transferring funds through Paynow
    @Transactional
    public String TopUp(Long accountNumber, Double amount, Long phoneNumber) {
        Customer customer = customerRepo.findCustomerByAccountNumber(accountNumber);
        if (customer == null) {
            throw new RuntimeException("Customer not found!");
        }

        // Initialize RestTemplate for making the API call
        RestTemplate restTemplate = new RestTemplate();
        String paynowUrl = "https://api.paynow.co.zw/v2/transactions/init";

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Prepare the request body for Paynow API call
        String requestBody = "{"
                + "\"id\": \"YOUR_INTEGRATION_KEY\","
                + "\"reference\": \"Top Up Account #" + accountNumber + "\","
                + "\"amount\": \"" + amount + "\","
                + "\"phone\": \"" + phoneNumber + "\","
                + "\"email\": \"" + customer.getEmail() + "\""
                + "}";

        // Make the API request
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(paynowUrl, HttpMethod.POST, entity, String.class);

        // Check response status
        if (response.getStatusCode().is2xxSuccessful()) {
            // Payment succeeded, update customer balance
            customer.setBalance(customer.getBalance() + amount);
            customerRepo.save(customer); // Ensure the new balance is saved
            return "Top-up successful!";
        } else {
            // Payment failed, handle error
            return "Top-up failed. Please try again!";
        }
    }
}
