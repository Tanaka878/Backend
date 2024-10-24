package com.musungare.BackendForReact.CustomerService;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.Currency;
import com.musungare.BackendForReact.BankAccout.Service.BankAccountService;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import com.musungare.BackendForReact.paypalConfig.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepo customerRepo;
    private final PayPalService payPalService;
    private final BankAccountService bankAccountService;
    private final BankAccountRepo bankAccountRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, PayPalService payPalService, BankAccountService bankAccountService, BankAccountRepo bankAccountRepo) {
        this.customerRepo = customerRepo;
        this.payPalService = payPalService;
        this.bankAccountService = bankAccountService;
        this.bankAccountRepo = bankAccountRepo;
    }

    public Customer getCustomers(String email) {
        return customerRepo.findCustomerByEmail(email);
    }

    public void AddCustomer(Customer customer) {
        Random account = new Random();
        long random = account.nextInt(90000) + 10000;

        Optional<BankAccount> optionalAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(random));
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepo.findCustomerByEmail(customer.getEmail()));

        while (optionalAccount.isPresent()) {
            random = account.nextInt(90000) + 10000;
            optionalAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(random));
        }

        if (customerOptional.isPresent()) {
            throw new RuntimeException("Customer already exists");
        } else {
            //passed tests
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(random);
            bankAccount.setBalance(0.0);
            bankAccount.setEmail(customer.getEmail());
            bankAccount.setAccountType(Currency.ZIG);

            bankAccountService.createBankAccount(bankAccount);

            //customer.setAccountNumber(random);
            customerRepo.save(customer);
        }
    }

    @Transactional
    public void UpdateCustomerDetails(String email, Double balance) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer != null) {
            //TODO CHANGE VALUES TO AFFECT BALANCE
            //customer.setBalance(customer.getBalance() - balance);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public void TopUp(String email, Double amount, Long phoneNumber) {
        //use is to find account number
        Customer customer = customerRepo.findCustomerByEmail(email);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        try {
            BankAccount bankAccount = bankAccountRepo.findByEmail(email);
            String cancelUrl = "http://localhost:8080/cancel";
            String successUrl = "http://localhost:8080/success";
            Payment payment = payPalService.createPayment(amount, "USD", "paypal", "sale",
                    "Top-up for Customer " + bankAccount.getAccountNumber(), cancelUrl, successUrl);

        } catch (PayPalRESTException e) {
            logger.error("An error occurred while processing myMethod: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating PayPal payment: " + e.getMessage());
        }
    }
}
