package com.bank.evolve.repository;

import com.bank.evolve.entity.User;
import com.bank.evolve.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.evolve.entity.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderOrRecipient(User sender, User recipient);
    List<Transaction> findByTargetDateAndStatus(LocalDate targetDate, TransactionStatus status);
    List<Transaction> findByTargetDateBeforeAndStatus(LocalDate targetDate, TransactionStatus status);
    List<Transaction> findBySenderAndStatus(User sender, TransactionStatus status);
}
