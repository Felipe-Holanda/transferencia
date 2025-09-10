package com.bank.evolve.repository;

import com.bank.evolve.entity.TransferTaxes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferTaxesRepository extends JpaRepository<TransferTaxes, Long> {

    Optional<TransferTaxes> findByAmountDays(int amountDays);
}
