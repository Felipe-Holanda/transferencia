package com.bank.evolve.dto.response;

import com.bank.evolve.entity.User;
import com.bank.evolve.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminTransactionResponse {
    private Double amount;
    private String description;
    private double taxes;
    private UserDto sender;
    private UserDto recipient;
    private LocalDate targetDate;
    private String transactionHash;
    private TransactionStatus status;
}
