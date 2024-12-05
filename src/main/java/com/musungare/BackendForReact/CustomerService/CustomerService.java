package com.musungare.BackendForReact.CustomerService;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.Currency;
import com.musungare.BackendForReact.BankAccout.Service.BankAccountService;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import com.musungare.BackendForReact.Email.MailSenderService;
import com.musungare.BackendForReact.Utilities.RandomCodeGenerator;
import com.musungare.BackendForReact.paypalConfig.PayPalService;


import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepo customerRepo;
    private final BankAccountService bankAccountService;
    private final BankAccountRepo bankAccountRepo;
    private final MailSenderService mailService;


    private final MailSenderService mailSenderService;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, PayPalService payPalService,
                           BankAccountService bankAccountService,
                           BankAccountRepo bankAccountRepo, MailSenderService mailService,
                           MailSenderService mailSenderService) {
        this.customerRepo = customerRepo;
        this.bankAccountService = bankAccountService;
        this.bankAccountRepo = bankAccountRepo;
        this.mailService = mailService;
        this.mailSenderService = mailSenderService;
    }

    public Customer getCustomers(String email) {
        return customerRepo.findCustomerByEmail(email);
    }


    public void AddCustomer(Customer customer) {
        Random account = new Random();

// Generate a 10-digit account number
        long customerAccount = 1000000000L + account.nextLong(9000000000L);

        Optional<BankAccount> optionalAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(customerAccount));
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepo.findCustomerByEmail(customer.getEmail()));

// Ensure uniqueness by regenerating if the account number already exists
        while (optionalAccount.isPresent()) {
            customerAccount = 1000000000L + account.nextLong(9000000000L);
            optionalAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(customerAccount));
        }


        if (customerOptional.isPresent()) {
            throw new RuntimeException("Customer already exists");
        } else {
            //passed tests
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(customerAccount);
            bankAccount.setAccountType(Currency.ZIG);
            bankAccount.setEmail(customer.getEmail());
            bankAccount.setBalance(0);
            bankAccountService.createBankAccount(bankAccount);
            customerRepo.save(customer);
            //
                String to = customer.getEmail(); // recipient email
                String customerPassword = customer.getPassword();
                mailSenderService.sendSimpleMail(to, customerAccount,customerPassword);


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





    @Transactional
    public void ResetPassword(String email) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepo.findCustomerByEmail(email));
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            String otp = RandomCodeGenerator.generateCode(4);
            customer.setPassword(otp);
            customerRepo.save(customer);
            mailService.ResetPassword(otp, email);


        }
    }

    @Transactional
    public ResponseEntity<Customer> changePassword(String email, String password) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer != null) {
            customer.setPassword(password);
            return ResponseEntity.ok(customerRepo.save(customer));
        }
        else return ResponseEntity.notFound().build() ;
    }

    public ResponseEntity<Customer> getProfile(String email) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepo.findCustomerByEmail(email));
        return customerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
