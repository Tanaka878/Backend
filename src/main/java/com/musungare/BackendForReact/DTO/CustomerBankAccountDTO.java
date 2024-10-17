package com.musungare.BackendForReact.DTO;

import com.musungare.BackendForReact.BankAccout.Currency;

public class CustomerBankAccountDTO {


        // Customer fields
        private Long customerId;
        private String name;
        private String surname;
        private String email;

        // Bank fields
        private Long bankId;
        private Long accountNumber;
        private double balance;
        private Currency accountType;

        // Getters and Setters
        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Long getBankId() {
            return bankId;
        }

        public void setBankId(Long bankId) {
            this.bankId = bankId;
        }

        public Long getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(Long accountNumber) {
            this.accountNumber = accountNumber;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public Currency getAccountType() {
            return accountType;
        }

        public void setAccountType(Currency accountType) {
            this.accountType = accountType;
        }

}
