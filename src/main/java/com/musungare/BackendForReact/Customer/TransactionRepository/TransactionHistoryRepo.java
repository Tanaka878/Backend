package com.musungare.BackendForReact.Customer.TransactionRepository;

import com.musungare.BackendForReact.Customer.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory,Long> {
    List<TransactionHistory> findAllByAccountHolder(Long accountHolder);


}
