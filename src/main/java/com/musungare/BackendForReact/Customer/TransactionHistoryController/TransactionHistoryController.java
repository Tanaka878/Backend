package com.musungare.BackendForReact.Customer.TransactionHistoryController;


import com.musungare.BackendForReact.Customer.TransactionHistory;
import com.musungare.BackendForReact.Customer.TransactionHistoryService.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin("*")
@RequestMapping("transactionHistory")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }



    @CrossOrigin("*")
    @GetMapping(path = "/history/{accountHolderEmail}")
    public List<TransactionHistory> getTransactions(@PathVariable("accountHolderEmail")String email){
        return transactionHistoryService.getTransactionHistory(email);

    }

   @PostMapping(path = "/receiveHistory")
    public void saveTransactionHistory(@RequestBody TransactionHistory history){
        transactionHistoryService.SaveTransaction(history);

    }
}
