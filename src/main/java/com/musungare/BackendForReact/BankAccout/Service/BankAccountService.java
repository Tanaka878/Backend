package com.musungare.BackendForReact.BankAccout.Service;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Utilities.TransactionStatus;
import com.musungare.BackendForReact.Utilities.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
            ///updating history of sender
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setBankName(bankName);
           // transactionHistory.setAccountHolder(senderAccount);
            transactionHistory.setLocalDate(LocalDate.now());
            transactionHistory.setReceiver(receiverAccount);
            transactionHistory.setTransactionType(TransactionType.DEBIT);
            transactionHistory.setStatus(TransactionStatus.SUCCESS);
            transactionHistory.setAmount(Double.valueOf(amount));
            transactionHistory.setComment("Transaction Successful");
            bankAccountRepo.save(SenderAccount);

            ///updating history of receiver
            TransactionHistory ReceiverTransationHistory = new TransactionHistory();
            transactionHistory.setBankName(bankName);
             transactionHistory.setAccountHolder(senderAccount);
            transactionHistory.setLocalDate(LocalDate.now());
            //transactionHistory.setReceiver(receiverAccount);
            transactionHistory.setTransactionType(TransactionType.CREDIT);
            transactionHistory.setStatus(TransactionStatus.SUCCESS);
            transactionHistory.setAmount(Double.valueOf(amount));
            transactionHistory.setComment("Transaction Successful");
            bankAccountRepo.save(ReceiverAccount);
        }

    }
}
