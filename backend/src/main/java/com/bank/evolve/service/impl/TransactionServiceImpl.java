package com.bank.evolve.service.impl;

import com.bank.evolve.dto.Request.TransactionRequest;
import com.bank.evolve.dto.Response.TransactionResponse;
import com.bank.evolve.enums.TransactionDirection;
import com.bank.evolve.enums.TransactionTypes;
import com.bank.evolve.repository.TransactionRepository;
import com.bank.evolve.repository.UserRepository;
import com.bank.evolve.service.TransactionService;
import com.bank.evolve.entity.Transaction;
import com.bank.evolve.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.evolve.service.TransferTaxesService;
import com.bank.evolve.util.AppError;
import com.bank.evolve.util.HashUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    UserRepository userRepository;
    TransferTaxesService transferTaxesService;


    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(User user, User targetUser, TransactionRequest transactionRequest) {

        Date today = new Date();
        Date targetDate = transactionRequest.getTransactionDate();

        if(targetDate.before(today)){
            throw new AppError("A data da transação não pode ser anterior a data atual.", HttpStatus.BAD_REQUEST);
        }

        Double amount = transactionRequest.getAmount();
        if(amount <= 0){
            throw new AppError("O valor da transação deve ser maior que zero.", HttpStatus.BAD_REQUEST);
        }

        long diffInMillies = targetDate.getTime() - today.getTime();
        long totalDays = diffInMillies / (1000 * 60 * 60 * 24);
        if(totalDays < 0){
            throw new AppError("A data da transação não pode ser anterior a data atual.", HttpStatus.BAD_REQUEST);
        }

        Double totalWithFees = transferTaxesService.calculateTax(transactionRequest.getAmount(), totalDays);

        if(user.getBalance() < totalWithFees){
            throw new AppError("Você não tem saldo suficiente para realizar essa transação.", HttpStatus.FORBIDDEN);
        }

        String transactionHash = HashUtil.transactionHash(user.getAccountNumber(), targetUser.getAccountNumber(), amount);

        Transaction transaction = new Transaction(
                amount,
                transactionRequest.getDescription() == null ? "Nenhuma descrição fornecida." : transactionRequest.getDescription(),
                totalWithFees - amount,
                user,
                targetUser,
                transactionRequest.getTransactionDate(),
                transactionHash
        );

        user.setBalance(user.getBalance() - totalWithFees);
        userRepository.save(user);

        targetUser.setBalance(targetUser.getBalance() + amount);
        userRepository.save(targetUser);
        return transaction;
    }

    public Transaction deposit(User user, double amount){
        if(amount <= 0){
            throw new AppError("O valor do depósito deve ser maior que zero.", HttpStatus.BAD_REQUEST);
        }

        String transactionHash = HashUtil.transactionHash("DEPOSIT", user.getAccountNumber(), amount);

        Transaction transaction = new Transaction(
                amount,
                "Depósito em conta",
                0.0,
                null,
                user,
                new Date(),
                transactionHash
        );

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return transaction;
    }

    public List<TransactionResponse> getTransactionsByUser(User user){

        List<Transaction> transactions = transactionRepository.findBySenderOrRecipient(user, user);
        List<TransactionResponse> transactionResponses = new ArrayList<>();

        for(Transaction transaction : transactions) {
            if(transaction.getSender() == null) {
                //Deposito
                transactionResponses.add(new TransactionResponse(
                        "Sistema",
                        transaction.getTransactionHash(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        TransactionDirection.INCOMING,
                        transaction.getTargetDate().toString(),
                        TransactionTypes.DEPOSIT));
            }else if(transaction.getSender().getId().equals(user.getId())) {
                //Envio
                transactionResponses.add(new TransactionResponse(
                        transaction.getRecipient().getFullName(),
                        transaction.getTransactionHash(),
                        transaction.getDescription(),
                        transaction.getAmount() + transaction.getTaxes(),
                        TransactionDirection.OUTGOING,
                        transaction.getTargetDate().toString(),
                        TransactionTypes.TRANSFER));
            }else {
                //Recebimento
                transactionResponses.add(new TransactionResponse(
                        transaction.getSender().getFullName(),
                        transaction.getTransactionHash(),
                        transaction.getDescription(),
                        transaction.getAmount() + transaction.getTaxes(),
                        TransactionDirection.OUTGOING,
                        transaction.getTargetDate().toString(),
                        TransactionTypes.TRANSFER));
            }
        }

        return transactionResponses;
    }

}
