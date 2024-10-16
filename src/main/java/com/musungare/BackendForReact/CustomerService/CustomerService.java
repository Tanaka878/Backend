package com.musungare.BackendForReact.CustomerService;

import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import com.musungare.BackendForReact.paypalConfig.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final PayPalService payPalService;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, PayPalService payPalService) {
        this.customerRepo = customerRepo;
        this.payPalService = payPalService;
    }

    public Customer getCustomers(Long accountNumber) {
        return customerRepo.findCustomerByAccountNumber(accountNumber);
    }

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

    @Transactional
    public void UpdateCustomerDetails(Long accountNumber, Double balance) {
        Customer customer = customerRepo.findCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            customer.setBalance(customer.getBalance() - balance);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public Payment TopUp(long accountNumber, Double amount, Long phoneNumber) {
        Customer customer = customerRepo.findCustomerByAccountNumber(accountNumber);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        try {
            String cancelUrl = "http://localhost:8080/cancel";
            String successUrl = "http://localhost:8080/success";
            Payment payment = payPalService.createPayment(amount, "USD", "paypal", "sale",
                    "Top-up for Customer " + accountNumber, cancelUrl, successUrl);

            return payment;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating PayPal payment: " + e.getMessage());
        }
    }
}
