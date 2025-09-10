package com.bank.evolve.service;

import com.bank.evolve.dto.Request.TransactionRequest;
import com.bank.evolve.dto.Response.TransactionResponse;
import com.bank.evolve.entity.Transaction;
import com.bank.evolve.entity.User;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(User user, User targetUser, TransactionRequest transactionRequest);
    Transaction deposit(User user, double amount);
    List<TransactionResponse> getTransactionsByUser(User user);
}
