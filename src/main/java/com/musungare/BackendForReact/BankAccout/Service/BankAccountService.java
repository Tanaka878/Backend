package com.musungare.BackendForReact.BankAccout.Service;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    BankAccountRepo bankAccountRepo;


    @Autowired
    public BankAccountService(BankAccountRepo bankAccountRepo) {
        this.bankAccountRepo = bankAccountRepo;
    }

    @Transactional
    public void createBankAccount(BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);
    }

    @Transactional
    public void interBankTransfer(Long senderAccount, Long receiverAccount, Long amount, String bankName) {

        BankAccount SenderAccount = bankAccountRepo.findByAccountNumber(senderAccount);
        BankAccount ReceiverAccount = bankAccountRepo.findByAccountNumber(receiverAccount);

        double balance = SenderAccount.getBalance();

        if (amount > balance) {
            System.out.println("Amount is greater than balance");
        }
        else {
            SenderAccount.setBalance(balance - amount);
            ReceiverAccount.setBalance(SenderAccount.getBalance()+ amount);
            System.out.println("Transaction Successful");
        }

    }
}
