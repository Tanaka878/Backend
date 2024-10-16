package com.musungare.BackendForReact.Customer;

import jakarta.persistence.*;


import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Long accountNumber;
    private String password;
    private double balance;
    //LocalDateTime createdAt;

    @OneToMany(mappedBy="customerList")
    private List<TransactionHistory> transactionHistory;



    public Customer() {
    }

    public Customer(Long id, String name,
                    String surname, String email,
                    Long accountNumber, String password,
                    double balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
    }

    public Customer(String name,
                    String surname, String email,
                    Long accountNumber, String password,
                    double balance) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
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

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", accountNumber=" + accountNumber +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
