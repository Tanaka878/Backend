package com.musungare.BackendForReact.CustomerRepository;

import com.musungare.BackendForReact.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {



    Customer findCustomerByEmail(String email);

    Customer findCustomerByAccountNumber(long accountNumber);
    
}
