package com.bank.evolve.dto.response;

import com.bank.evolve.enums.TransactionDirection;
import com.bank.evolve.enums.TransactionStatus;
import com.bank.evolve.enums.TransactionTypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {

    public TransactionResponse(String senderName, String transactionId, String description, double amount, TransactionDirection direction, String date, TransactionTypes type, TransactionStatus status) {
        this.status = status;
        this.senderName = senderName;
        this.transactionId = transactionId;
        this.description = description;
        this.amount = amount;
        this.direction = direction;
        this.date = date;
        this.type = type;
    }

    private String senderName;
    private String transactionId;
    private String description;
    private double amount;
    private TransactionDirection direction;
    private String date;
    private TransactionTypes type;
    private TransactionStatus status;
}
