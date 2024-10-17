package com.musungare.BackendForReact.BankAccout.Service;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    BankAccountRepo bankAccountRepo;

    @Autowired
    public BankAccountService(BankAccountRepo bankAccountRepo) {
        this.bankAccountRepo = bankAccountRepo;
    }

    public void createBankAccount(BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);
    }

}
