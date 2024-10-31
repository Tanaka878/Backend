package com.musungare.BackendForReact.Customer.TransactionHistoryService;

import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Customer.TransactionRepository.TransactionHistoryRepo;
import com.musungare.BackendForReact.Utilities.TransactionStatus;
import com.musungare.BackendForReact.Utilities.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionHistoryService {

    private final TransactionHistoryRepo transactionHistoryRepo;

    public TransactionHistoryService(TransactionHistoryRepo transactionHistoryRepo) {
        this.transactionHistoryRepo = transactionHistoryRepo;
    }

    public List<TransactionHistory> getTransactionHistory(Long accountHolder) {
        return transactionHistoryRepo.findAllByAccountHolder(accountHolder);
    }

    @Transactional
    public void SaveTransaction(TransactionHistory history) {




        transactionHistoryRepo.save(history);
    }
}
