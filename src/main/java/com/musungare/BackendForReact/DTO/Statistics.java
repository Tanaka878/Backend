package com.musungare.BackendForReact.DTO;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import lombok.Data;

import java.util.List;

@Data
public class Statistics {
   private List<Loan> loans;
   private int pending;
   private int users;



}
