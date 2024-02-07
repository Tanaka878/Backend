package com.musungare.BackendForReact.CustomerController;

import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerService.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class CustomerController {


    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "{accountNumber}")
    public Customer GetCustomers(@PathVariable("accountNumber") Long accountNumber){
        return customerService.getCustomers(accountNumber);
    }

    @PostMapping()
    public void AddCustomers(@RequestBody Customer customer){
        customerService.AddCustomer(customer);

    }

    @PutMapping(path = "{accountNumber}")
    public void UpdateDetails(@PathVariable("accountNumber") Long accountNUmber,
                              @RequestParam(required = false) Double balance){
        customerService.UpdateCustomerDetails(accountNUmber,balance);
    }
}
