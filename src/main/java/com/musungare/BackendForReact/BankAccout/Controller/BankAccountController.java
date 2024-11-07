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

    BankAccountService bankAccountService;
    BankAccountRepo bankAccountRepo;

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
            return ResponseEntity.ok("{\"message\": \"Transfer successful!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Bank account not found\"}");
        }
    }

    // POST request to top up account
    @PostMapping("/top-up/{email}/{amount}/{phoneNumber}")
    public ResponseEntity<String> topUpAccount(@PathVariable String email,
                                               @PathVariable Double amount,
                                               @PathVariable Long phoneNumber) {
        Optional<BankAccount> bankAccount = Optional.ofNullable(bankAccountRepo.findByEmail(email));

        // Check if bank account exists
        if (bankAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found.");
        }

        // (Optional) Check if phone number matches
//        if (!bankAccount.get().getPhoneNumber().equals(phoneNumber)) {
//            return ResponseEntity.badRequest().body("Phone number does not match.");
//        }

        // Top up the account
        try {
            bankAccountService.TopUp(email, amount, phoneNumber);
            return ResponseEntity.ok("Top-up successful!");
        } catch (Exception e) {
            // Handle potential exceptions in the service layer
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Top-up failed: " + e.getMessage());
        }
    }


    // GET request to handle successful payment
    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess() {
        return ResponseEntity.ok("Payment successful!");
    }

    // GET request to handle canceled payment
    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment canceled!");
    }

    @PostMapping("/buyAirtime/{accountNumber}/{phoneNumber}/{amount}")
    public ResponseEntity<String> buyAirtime(
            @PathVariable Long accountNumber,
            @PathVariable Long phoneNumber,
            @PathVariable Double amount) {

        Optional<BankAccount> bankAccount = Optional.ofNullable(bankAccountRepo.findByAccountNumber(accountNumber));

        if (bankAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found.");
        } else {
            bankAccountService.buyAirtime(accountNumber, phoneNumber, amount);
            return ResponseEntity.ok("Airtime purchase successful.");
        }
    }

}
