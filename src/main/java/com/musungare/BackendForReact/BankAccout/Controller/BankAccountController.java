package com.musungare.BackendForReact.BankAccout.Controller;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.Service.BankAccountService;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Utilities.InterBankTransferRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/banking")
@Getter
@Setter
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountRepo bankAccountRepo;

    @Autowired
    public BankAccountController(BankAccountRepo bankAccountRepo, BankAccountService bankAccountService) {
        this.bankAccountRepo = bankAccountRepo;
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/interbankTransfer")
    public ResponseEntity<String> interbankTransfer(@RequestBody InterBankTransferRequest transferRequest) {
        Optional<BankAccount> findSenderAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(transferRequest.getSenderAccount()));
        Optional<BankAccount> findReceiverAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(transferRequest.getReceiverAccount()));

        if (findSenderAccount.isPresent() && findReceiverAccount.isPresent()) {
            bankAccountService.interBankTransfer(
                    transferRequest.getSenderAccount(),
                    transferRequest.getReceiverAccount(),
                    transferRequest.getAmount(),
                    transferRequest.getBankName()
            );
            return ResponseEntity.ok("Transfer successful!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account not found");
        }
    }
}
