package com.musungare.BackendForReact.BankAccout.Loan;

import jakarta.persistence.*;
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
    private Long loanId;

    private LocalDate loanDate;
    private long loanAmount;
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;
    private int paybackPeriod;
    private double interestRate;
    private double monthlyInstallment;
    private int monthsLeft;
    private String email;



}
