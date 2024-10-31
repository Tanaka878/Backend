package com.musungare.BackendForReact.Customer;

import com.musungare.BackendForReact.Utilities.TransactionStatus;
import com.musungare.BackendForReact.Utilities.TransactionType;
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
    private Long receiver;
    private Double amount;
    private LocalDate localDate;
    private TransactionType transactionType;
    private TransactionStatus status;
    private String comment;
    private String bankName;
    private

    public TransactionHistory(Long id, Long accountHolder, Long receiver,
                              LocalDate localDate, Double amount,
                              TransactionType transactionType, TransactionStatus status, String comment,
                              String bankName) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.receiver = receiver;
        this.localDate = LocalDate.now();
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
        this.comment = comment;
        this.bankName = bankName;
    }
}
