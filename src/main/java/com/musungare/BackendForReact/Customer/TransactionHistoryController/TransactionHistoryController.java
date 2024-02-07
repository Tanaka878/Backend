package com.musungare.BackendForReact.Customer.TransactionHistoryController;


import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Customer.TransactionHistoryService.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin("http://localhost:3000")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }



    @CrossOrigin("http://localhost:3000")
    @GetMapping(path = "/history/{accountHolder}")
    public List<TransactionHistory> getTransactions(@PathVariable("accountHolder")Long accountHolder){
        return transactionHistoryService.getTransactionHistory(accountHolder);

    }

    @PostMapping(path = "/receiveHistory")
    public void saveTransactionHistory(@RequestBody TransactionHistory history){
        transactionHistoryService.SaveTransaction(history);

    }
}
