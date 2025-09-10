package com.bank.evolve.repository;

import com.bank.evolve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.evolve.entity.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderOrRecipient(User sender, User recipient);
}
