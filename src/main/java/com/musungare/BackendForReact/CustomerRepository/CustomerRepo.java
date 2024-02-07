package com.musungare.BackendForReact.CustomerRepository;

import com.musungare.BackendForReact.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Customer findCustomerByAccountNumber(Long accountNumber);

    Customer existsCustomersByAccountNumber(Long accountNumber);
    
}
