package com.musungare.BackendForReact.Admin;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import com.musungare.BackendForReact.BankAccout.Loan.Repository.LoanRepository;
import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.DTO.AdminData;
import com.musungare.BackendForReact.DTO.LoanDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")

public class AdminController {

    private final AdminService adminService;
    private final LoanRepository loanRepo;

    @Autowired
    public AdminController(AdminService adminService, LoanRepository loanRepo) {
        this.adminService = adminService;
        this.loanRepo = loanRepo;
    }
    @PostMapping("/getAdmin")
    public ResponseEntity<Admin> getAdmin(@RequestBody AdminData adminData) {
        return adminService.getAdmin(adminData.getEmail(), adminData.getPassword());
    }

    @GetMapping("/getUsers/")
    public ResponseEntity<List<Customer>> getUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/getLoans")
    public ResponseEntity<List<Loan>> getLoans() {
        return adminService.getLoans();
    }

    @GetMapping("/loans")
    public ResponseEntity<LoanDataDTO> getLoan(@RequestParam Long loanId, @RequestParam String email) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getEmail().equals(email)) {
            throw new RuntimeException("Email does not match the loan record");
        }

        LoanDataDTO loanDataDTO = new LoanDataDTO();
        loanDataDTO.setLoanId(loan.getLoanId());
        loanDataDTO.setEmail(loan.getEmail());
        loanDataDTO.setAmount(loan.getLoanAmount());
        loanDataDTO.setPayback(loan.getPaybackPeriod());
        loanDataDTO.setLoanType(loan.getLoanType());

        return ResponseEntity.ok(loanDataDTO);
    }


    @PutMapping("/acceptLoan/{loanId}/{email}")
    public ResponseEntity<String> acceptLoan(@PathVariable Long loanId, @PathVariable String email) {
        return adminService.acceptLoan(loanId, email);
    }

    @PutMapping("/rejectLoan/{loanId}/{email}")
    public ResponseEntity<String> rejectLoan(@PathVariable Long loanId, @PathVariable String email) {

        return adminService.rejectLoan(loanId,email);
    }
}
