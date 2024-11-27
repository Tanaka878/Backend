package com.musungare.BackendForReact.Admin;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import com.musungare.BackendForReact.BankAccout.Loan.Repository.LoanRepository;
import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final CustomerRepo customerRepo;
   private final LoanRepository loanRepo;

    @Autowired
    public AdminService(AdminRepository adminRepository, CustomerRepo customerRepo, LoanRepository loanRepo) {
        this.adminRepository = adminRepository;
        this.customerRepo = customerRepo;
        this.loanRepo = loanRepo;
    }

    public ResponseEntity<Admin> getAdmin(String email, String password) {
        Admin byEmail = adminRepository.findByEmail(email);

        if (byEmail != null) {
            if (byEmail.getPassword().equals(password)) {
                return ResponseEntity.ok(byEmail);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        } else {

            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Customer>> getAllUsers() {
        return ResponseEntity.ok(customerRepo.findAll());
    }

    public ResponseEntity<List<Loan>> getLoans() {
        return ResponseEntity.ok(loanRepo.findAll());

    }
}
