package com.musungare.BackendForReact.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class TransactionHistory {


    private Long accountHolder;
    private String receiver;

   private Double amount;

   private LocalDate localDate;
    private Long id;
    @ManyToOne
    public Customer customerList ;


    public TransactionHistory() {
    }

    public TransactionHistory(Long accountHolder, String receiver, Double amount, LocalDate localDate) {
        this.accountHolder = accountHolder;
        this.receiver = receiver;
        this.amount = amount;
        this.localDate = localDate;
    }

    public TransactionHistory(Long accountHolder,
                              String receiver, Double amount,
                              LocalDate localDate, Long id) {
        this.accountHolder = accountHolder;
        this.receiver = receiver;
        this.amount = amount;
        this.localDate = localDate;
        this.id = id;
    }



    public Long getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Long accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
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
        return "TransactionHistory{" +
                "accountHolder=" + accountHolder +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                ", localDate=" + localDate +
                ", id=" + id +
                ", customerList=" + customerList +
                '}';
    }
}
