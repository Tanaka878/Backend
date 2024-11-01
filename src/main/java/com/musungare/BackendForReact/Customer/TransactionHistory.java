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
    private String ownerEmail;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String comment;
    private String bankName;
}
