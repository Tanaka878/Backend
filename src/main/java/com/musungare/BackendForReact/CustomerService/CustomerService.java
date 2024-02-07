package com.musungare.BackendForReact.CustomerService;

import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer getCustomers(Long accountNumber) {
        return  customerRepo.findCustomerByAccountNumber(accountNumber);
    }

    public void AddCustomer(Customer customer) {

        customerRepo.save(customer);
    }

    @Transactional
    public void UpdateCustomerDetails(Long accountNUmber, Double balance) {

        Customer customer = customerRepo.findCustomerByAccountNumber(accountNUmber);
        customer.setBalance(customer.getBalance() - balance);


    }
}
