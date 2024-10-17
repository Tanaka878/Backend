package com.musungare.BackendForReact.BankAccout.repo;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, Integer> {
    BankAccount findByAccountNumber(Long accountNumber);
    BankAccount findByAccountNumberAndAccountType(Long accountNumber, Currency accountType);
    BankAccount findByEmail(String email);

}
