package com.musungare.BackendForReact.BankAccout.Loan.Controller;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import com.musungare.BackendForReact.BankAccout.Loan.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/loan/")
@CrossOrigin("*")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/save/applyLoan")
    public ResponseEntity<String> applyLoan(@RequestBody Loan loan) {
        return loanService.saveApplication(loan);
    }

    @RequestMapping("/getLoanDetails/{email}")
    public ResponseEntity<List<Loan>> getLoanDetails(@PathVariable String email) {

        return loanService.getLoanDetails(email);
    }

}
