package com.musungare.BackendForReact.Customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountHolder;
    private String receiver;
    private Double amount;

    private LocalDate localDate;

    // Corrected constructor
    public TransactionHistory(Long accountHolder, String receiver, double amount, LocalDate localDate) {
        this.accountHolder = accountHolder;
        this.receiver = receiver;
        this.amount = amount;
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "accountHolder=" + accountHolder +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                ", localDate=" + localDate +
                ", id=" + id +
                '}';
    }
}
