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
import java.time.LocalDate;
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
        System.out.println("The email :" + email);
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
            System.out.println("Customer not found");
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Customer customer) {
        //setting date joined
        Customer customer1 = customer;
        customer1.setLocalDate(LocalDate.now());
        // Check if the customer already exists using email
        Optional<Customer> customerExists = Optional.ofNullable(customerService.getCustomers(customer.getEmail()));

        if (customerExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with this email already exists");
        }

        // If the customer doesn't exist, proceed to create the account
        customerService.AddCustomer(customer1);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully");
    }

    @RequestMapping
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

    @GetMapping("/getProfile/{email}")
    public ResponseEntity<Customer> getProfile(@PathVariable String email) {
       return customerService.getProfile(email);
    }

}
