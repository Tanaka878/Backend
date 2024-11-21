package com.musungare.BackendForReact.BankAccout.Loan.Repository;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findByEmail(String email);
}
