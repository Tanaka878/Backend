package com.musungare.BackendForReact.BankAccout.Service;



import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Customer.TransactionRepository.TransactionHistoryRepo;
import com.musungare.BackendForReact.Utilities.TransactionStatus;
import com.musungare.BackendForReact.Utilities.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Service
public class BankAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    private final BankAccountRepo bankAccountRepo;
    private final TransactionHistoryRepo transactionHistoryRepo;

    @Autowired
    public BankAccountService(BankAccountRepo bankAccountRepo, TransactionHistoryRepo transactionHistoryRepo) {
        this.bankAccountRepo = bankAccountRepo;
        this.transactionHistoryRepo = transactionHistoryRepo;
    }

    @Transactional
    public void createBankAccount(BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);
    }

    @Transactional
    public void interBankTransfer(Long senderAccountNumber, Long receiverAccountNumber, Long amount, String bankName) {
        BankAccount senderAccount = bankAccountRepo.findByAccountNumber(senderAccountNumber);
        BankAccount receiverAccount = bankAccountRepo.findByAccountNumber(receiverAccountNumber);

        if (senderAccount.getBalance() < amount) {
            logger.info("Transaction failed: insufficient funds in sender's account.");
            return;
        }

        // Update balances
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        logger.info("Transaction Successful: {} transferred from account {} to account {}", amount, senderAccountNumber, receiverAccountNumber);

        // Record transaction histories
        saveTransactionHistory(senderAccountNumber, receiverAccountNumber, bankName, amount, TransactionType.DEBIT, senderAccount);
        saveTransactionHistory(receiverAccountNumber, senderAccountNumber, bankName, amount, TransactionType.CREDIT, receiverAccount);
    }

    private void saveTransactionHistory(Long accountNumber, Long counterpartAccount, String bankName, Long amount,
                                        TransactionType transactionType, BankAccount account) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setBankName(bankName);
        transactionHistory.setAccountHolder(accountNumber);
        transactionHistory.setLocalDate(LocalDate.now());
        transactionHistory.setReceiver(counterpartAccount);
        transactionHistory.setTransactionType(transactionType);
        transactionHistory.setStatus(TransactionStatus.SUCCESS);
        transactionHistory.setAmount(Double.valueOf(amount));
        transactionHistory.setOwnerEmail(account.getEmail());
        transactionHistory.setComment("Transaction Successful");

        transactionHistoryRepo.save(transactionHistory);
    }
}
