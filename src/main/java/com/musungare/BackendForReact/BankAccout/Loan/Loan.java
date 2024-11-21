package com.musungare.BackendForReact.BankAccout.Loan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String loanId;

    private LocalDate loanDate;
    private BigDecimal loanAmount;
    private LoanType loanType;
    private LoanStatus loanStatus;
    private int paybackPeriod;
    private double interestRate;
    private double monthlyInstallment;
    private int monthsLeft;
    private String email;



}
