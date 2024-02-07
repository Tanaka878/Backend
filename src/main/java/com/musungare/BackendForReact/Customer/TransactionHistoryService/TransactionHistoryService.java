package com.musungare.BackendForReact.Customer.TransactionHistoryService;

import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Customer.TransactionRepository.TransactionHistoryRepo;
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

    public void SaveTransaction(TransactionHistory history) {

        //extracting  the body of the request.
        Long acc = history.getAccountHolder();
        double bal = history.getAmount();
        String receiver = history.getReceiver();
        LocalDate date = LocalDate.now();

      TransactionHistory transactionHistory=
              new TransactionHistory(acc,receiver,bal,date);
        transactionHistoryRepo.save(transactionHistory);
    }
}
