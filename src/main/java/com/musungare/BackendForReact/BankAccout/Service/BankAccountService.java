package com.musungare.BackendForReact.BankAccout.Service;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;

import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Customer.TransactionRepository.TransactionHistoryRepo;
import com.musungare.BackendForReact.Utilities.TransactionStatus;
import com.musungare.BackendForReact.Utilities.TransactionType;
import com.musungare.BackendForReact.paypalConfig.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
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
    private final PayPalService payPalService;

    @Autowired
    public BankAccountService(BankAccountRepo bankAccountRepo, TransactionHistoryRepo transactionHistoryRepo, PayPalService payPalService) {
        this.bankAccountRepo = bankAccountRepo;
        this.transactionHistoryRepo = transactionHistoryRepo;
        this.payPalService = payPalService;
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
            String comment = "Transaction failed: insufficient funds.";
            saveTransactionHistory(receiverAccountNumber, senderAccountNumber, bankName, amount, TransactionType.CREDIT, receiverAccount, comment,TransactionStatus.FAILED);

            return;
        }

        // Update balances
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        logger.info("Transaction Successful: {} transferred from account {} to account {}", amount, senderAccountNumber, receiverAccountNumber);

        String comment = "Transfer Successful";
        // Record transaction histories
        saveTransactionHistory(senderAccountNumber, receiverAccountNumber, bankName, amount, TransactionType.DEBIT, senderAccount, comment,TransactionStatus.SUCCESS);
        saveTransactionHistory(receiverAccountNumber, senderAccountNumber, bankName, amount, TransactionType.CREDIT, receiverAccount, comment, TransactionStatus.SUCCESS);
    }

    private void saveTransactionHistory(Long accountNumber, Long counterpartAccount, String bankName, Long amount,
                                        TransactionType transactionType, BankAccount account, String comment ,TransactionStatus status) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setBankName(bankName);
        transactionHistory.setAccountHolder(accountNumber);
        transactionHistory.setLocalDate(LocalDate.now());
        transactionHistory.setReceiver(counterpartAccount);
        transactionHistory.setTransactionType(transactionType);
        transactionHistory.setStatus(status);
        transactionHistory.setAmount(Double.valueOf(amount));
        transactionHistory.setOwnerEmail(account.getEmail());
        transactionHistory.setComment(comment);

        transactionHistoryRepo.save(transactionHistory);
    }


    @Transactional
    public void TopUp(String email, Double amount, Long phoneNumber) {
        BankAccount bankAccount = bankAccountRepo.findByEmail(email);



        try {

            String cancelUrl = "http://localhost:8080/cancel";
            String successUrl = "http://localhost:8080/success";
            Payment payment = payPalService.createPayment(amount, "USD", "paypal", "sale",
                    "Top-up for Customer " + bankAccount.getAccountNumber(), cancelUrl, successUrl);
            bankAccount.setBalance(bankAccount.getBalance() + amount);
            bankAccountRepo.save(bankAccount);

            //saving transaction history
            saveTransactionHistory(bankAccount.getAccountNumber(),995757838L,"STEWARD BANK", amount.longValue(), TransactionType.CREDIT,bankAccount,"Top Successful");


        } catch (PayPalRESTException e) {
            logger.error("An error occurred while processing myMethod: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating PayPal payment: " + e.getMessage());
        }
    }


    @Transactional
    public void buyAirtime(Long accountNumber, Long phoneNumber, Double amount) {
        BankAccount bankAccount = bankAccountRepo.findByAccountNumber(accountNumber);

        if (bankAccount.getBalance() < amount) {
            logger.info("Transaction failed: insufficient funds.");
            saveTransactionHistory(accountNumber, 30744667L,"STANBIC BANK", amount.longValue(), TransactionType.DEBIT,bankAccount,"Insufficient Balance",TransactionStatus.FAILED);

        }
        else {
            double remainingBalance = bankAccount.getBalance() - amount;
            bankAccount.setBalance(remainingBalance);
            bankAccountRepo.save(bankAccount);
            //saving transaction history
            saveTransactionHistory(accountNumber, 30744667L,"STANBIC BANK", amount.longValue(), TransactionType.DEBIT,bankAccount,"Bought Airtime",TransactionStatus.SUCCESS);


        }

    }
}
