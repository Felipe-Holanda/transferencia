package com.bank.evolve.dto.Response;

import com.bank.evolve.enums.TransactionDirection;
import com.bank.evolve.enums.TransactionTypes;

public class TransactionResponse {

    public TransactionResponse(String senderName, String transactionId, String description, double amount, TransactionDirection direction, String date, TransactionTypes type) {
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
}
