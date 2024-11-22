package com.musungare.BackendForReact.BankAccout.Loan.Repository;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByEmail(String email);
}
