package com.musungare.BackendForReact.Admin;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
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

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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

    @GetMapping("/getLoanDetails/{loanId}/{email}")
    public ResponseEntity<LoanDataDTO> getLoanDetails(@PathVariable Long loanId, @PathVariable String email) {
        Loan loan = adminService.getLoan(loanId,email).getBody();
        LoanDataDTO loanDataDTO = new LoanDataDTO();
        if (loan != null) {
            loanDataDTO.setLoanId(loan.getLoanId());
            loanDataDTO.setLoanType(loan.getLoanType());
            loanDataDTO.setPayback(loan.getPaybackPeriod());
            loanDataDTO.setEmail(loan.getEmail());
        }

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
