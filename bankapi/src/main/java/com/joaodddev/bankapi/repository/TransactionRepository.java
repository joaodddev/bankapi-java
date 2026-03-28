package com.joaodddev.bankapi.repository;

import com.joaodddev.bankapi.model.Account;
import com.joaodddev.bankapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);

    List<Transaction> findByAccountAndTransactionDateBetween(Account account, LocalDateTime start, LocalDateTime end);
}