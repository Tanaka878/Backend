package com.musungare.BackendForReact.DTO;

import com.musungare.BackendForReact.BankAccout.Loan.LoanType;
import lombok.Data;

@Data
public class LoanDataDTO {

    private Long loanId;
    private String email;
    private double amount;
    private int payback;
    private LoanType loanType;
}
