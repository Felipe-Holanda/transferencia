package com.bank.evolve.repository;

import com.bank.evolve.entity.User;
import com.bank.evolve.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bank.evolve.entity.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.sender = :user OR t.recipient = :user")
    List<Transaction> findAllFromUser(@Param("user") User sender);
    List<Transaction> findByTargetDateAndStatus(LocalDate targetDate, TransactionStatus status);
    List<Transaction> findByTargetDateBeforeAndStatus(LocalDate targetDate, TransactionStatus status);
    List<Transaction> findBySenderAndStatus(User sender, TransactionStatus status);
}
