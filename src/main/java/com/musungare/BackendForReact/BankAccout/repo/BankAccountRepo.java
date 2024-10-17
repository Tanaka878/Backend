package com.musungare.BackendForReact.BankAccout.repo;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, Integer> {
    BankAccount findByAccountNumber(Long accountNumber);

}
