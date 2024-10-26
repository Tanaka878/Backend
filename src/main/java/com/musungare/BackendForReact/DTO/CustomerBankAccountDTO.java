package com.musungare.BackendForReact.DTO;

import com.musungare.BackendForReact.BankAccout.Currency;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerBankAccountDTO {


    // Getters and Setters
    // Customer fields
        private Long customerId;
        private String name;
        private String surname;
        private String email;
        private String password;

        // Bank fields
        private Long bankId;
        private Long accountNumber;
        private double balance;
        private Currency accountType;

}
