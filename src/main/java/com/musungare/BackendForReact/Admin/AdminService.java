package com.musungare.BackendForReact.Admin;

import com.musungare.BackendForReact.BankAccout.BankAccount;
import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import com.musungare.BackendForReact.BankAccout.Loan.LoanStatus;
import com.musungare.BackendForReact.BankAccout.Loan.Repository.LoanRepository;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import com.musungare.BackendForReact.Customer.Customer;
import com.musungare.BackendForReact.CustomerRepository.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final CustomerRepo customerRepo;
   private final LoanRepository loanRepo;
   private final BankAccountRepo bankAccountRepo;

    @Autowired
    public AdminService(AdminRepository adminRepository, CustomerRepo customerRepo, LoanRepository loanRepo, BankAccountRepo bankAccountRepo) {
        this.adminRepository = adminRepository;
        this.customerRepo = customerRepo;
        this.loanRepo = loanRepo;
        this.bankAccountRepo = bankAccountRepo;
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

    public ResponseEntity<Loan> getLoan(Long loanId, String email) {
       return ResponseEntity.ok(loanRepo.findById(loanId).get());
    }

    @Transactional
    public ResponseEntity<String> acceptLoan(Long loanId, String email) {
        Loan loan = loanRepo.findById(loanId).get();
        loan.setLoanStatus(LoanStatus.APPROVED);
        loanRepo.save(loan);
        long loanAmount = loan.getLoanAmount();

        //crediting the account in question
        Optional<BankAccount> bankAccount = Optional.ofNullable(bankAccountRepo.findByEmail(email));
        BankAccount bankAccount1 = bankAccount.get();
        bankAccount1.setBalance(bankAccount1.getBalance() + loanAmount);


        return ResponseEntity.ok("Loan Accepted");
    }

    @Transactional
    public ResponseEntity<String> rejectLoan(Long loanId, String email) {
        Loan loan = loanRepo.findById(loanId).get();
        loan.setLoanStatus(LoanStatus.DECLINED);
        loanRepo.save(loan);
        return ResponseEntity.ok("Loan Rejected");

    }
}
