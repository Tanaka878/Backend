package com.musungare.BackendForReact.CustomerController;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerService.CustomerService;
import com.musungare.BackendForReact.DTO.CustomerBankAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;
    private final BankAccountRepo bankAccountRepo;

    @Autowired
    public CustomerController(CustomerService customerService, BankAccountRepo bankAccountRepo) {
        this.customerService = customerService;
        this.bankAccountRepo = bankAccountRepo;
    }

    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email) {
        // Check if customer exists
        Optional<Customer> customerOptional = Optional.ofNullable(customerService.getCustomers(email));

        if (customerOptional.isPresent()) {
            // Proceed to reset the password
            customerService.ResetPassword(email);
            return ResponseEntity.ok("Reset password email sent successfully.");
        } else {
            // Customer not found, return a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }

    @PostMapping("/changePassword/{email}/{newPassword}")
    public ResponseEntity<Customer> changePassword(@PathVariable String email, @PathVariable String newPassword) {

        if (customerService.getCustomers(email) == null) {
            System.out.println("Password changed successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }else {
            customerService.changePassword(email, newPassword);
            return ResponseEntity.ok(customerService.getCustomers(email));

        }
    }

    // GET request to fetch customer details by account number
    @GetMapping("/{email}")
    public ResponseEntity<CustomerBankAccountDTO> getCustomerByAccountNumber(@PathVariable String email) {
        Customer customer = customerService.getCustomers(email);
        Optional<BankAccount> OptionalbankAccount = Optional.ofNullable(bankAccountRepo.findByEmail(email));

        System.out.println("Endpoint done");
        CustomerBankAccountDTO customerBankAccountDTO = new CustomerBankAccountDTO();


        if (customer != null && OptionalbankAccount.isPresent()) {
            BankAccount bankAccount = bankAccountRepo.findByEmail(email);

            customerBankAccountDTO.setAccountNumber(bankAccount.getAccountNumber());
            customerBankAccountDTO.setBalance(bankAccount.getBalance());
            customerBankAccountDTO.setEmail(email);
            customerBankAccountDTO.setSurname(customer.getSurname());
            customerBankAccountDTO.setName(customer.getName());
            customerBankAccountDTO.setPassword(customer.getPassword());
            customerBankAccountDTO.setCustomerId(customer.getId());


            return ResponseEntity.ok(customerBankAccountDTO);
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
        System.out.println("Endpoint created");
        // Check if the customer already exists using email
        Optional<Customer> customerExists = Optional.ofNullable(customerService.getCustomers(customer.getEmail()));

        if (customerExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with this email already exists");
        }

        // If the customer doesn't exist, proceed to create the account
        customerService.AddCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully");
    }

    @RequestMapping
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

}
