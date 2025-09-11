package com.bank.evolve.service.impl;

import com.bank.evolve.dto.request.TransactionRequest;
import com.bank.evolve.dto.response.AdminTransactionResponse;
import com.bank.evolve.dto.response.TransactionResponse;
import com.bank.evolve.dto.response.UserDto;
import com.bank.evolve.enums.TransactionDirection;
import com.bank.evolve.enums.TransactionStatus;
import com.bank.evolve.enums.TransactionTypes;
import com.bank.evolve.repository.TransactionRepository;
import com.bank.evolve.repository.UserRepository;
import com.bank.evolve.service.TransactionService;
import com.bank.evolve.entity.Transaction;
import com.bank.evolve.entity.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
                                  UserRepository userRepository,
                                  TransferTaxesService transferTaxesService) {
        this.transferTaxesService = transferTaxesService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(User user, User targetUser, TransactionRequest transactionRequest) {

        LocalDate today = LocalDate.now();
        LocalDate targetDate = transactionRequest.getTargetDate();
        if(targetDate.isBefore(today)){
            throw new AppError("A data da transação não pode ser anterior a data atual.", HttpStatus.BAD_REQUEST);
        }

        Double amount = transactionRequest.getAmount();
        if(amount <= 0){
            throw new AppError("O valor da transação deve ser maior que zero.", HttpStatus.BAD_REQUEST);
        }

        long days = ChronoUnit.DAYS.between(LocalDate.now(), transactionRequest.getTargetDate());
        if(days < 0){
            throw new AppError("A data da transação não pode ser anterior a data atual.", HttpStatus.BAD_REQUEST);
        }

        Double totalWithFees = transferTaxesService.calculateTax(transactionRequest.getAmount(), days);
        String transactionHash = HashUtil.transactionHash(user.getAccountNumber(), targetUser.getAccountNumber(), amount);
        Transaction transaction = new Transaction(
                amount,
                transactionRequest.getDescription() == null ? "Nenhuma descrição fornecida." : transactionRequest.getDescription(),
                totalWithFees - amount,
                user,
                targetUser,
                transactionRequest.getTargetDate(),
                transactionHash
        );

        // Se for transação instantânea (hoje), verificar saldo e processar imediatamente
        if (targetDate.equals(today)) {
            if(user.getBalance() < totalWithFees){
                throw new AppError("Você não tem saldo suficiente para realizar essa transação.", HttpStatus.FORBIDDEN);
            }
            
            user.setBalance(user.getBalance() - totalWithFees);
            userRepository.save(user);

            targetUser.setBalance(targetUser.getBalance() + amount);
            userRepository.save(targetUser);
        }
        
        return transactionRepository.save(transaction);
    }

    public Transaction deposit(User user, double amount){
        if(amount <= 0){
            throw new AppError("O valor do depósito deve ser maior que zero.", HttpStatus.BAD_REQUEST);
        }

        LocalDate today = LocalDate.now();
        String transactionHash = HashUtil.transactionHash("DEPOSIT", user.getAccountNumber(), amount);
        Transaction transaction = new Transaction(
                amount,
                "Depósito em conta",
                0.0,
                null,
                user,
                today,
                transactionHash
        );

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return transactionRepository.save(transaction);
    }

    public List<TransactionResponse> getTransactionsByUser(User user){

        List<Transaction> transactions = transactionRepository.findAllFromUser(user);
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
                        TransactionTypes.DEPOSIT,
                        transaction.getStatus()
                        ));
            }else if(transaction.getSender().getId().equals(user.getId())) {
                //Envio
                String statusSuffix = "";
                if (transaction.getStatus().equals(TransactionStatus.PENDING)) {
                    statusSuffix = " (Agendada)";
                } else if (transaction.getStatus().equals(TransactionStatus.CANCELLED)) {
                    statusSuffix = " (Cancelada)";
                }
                
                transactionResponses.add(new TransactionResponse(
                        transaction.getRecipient().getFullName(),
                        transaction.getTransactionHash(),
                        transaction.getDescription() + statusSuffix,
                        transaction.getAmount() + transaction.getTaxes(),
                        TransactionDirection.OUTGOING,
                        transaction.getTargetDate().toString(),
                        TransactionTypes.TRANSFER,
                        transaction.getStatus()));
            }else {
                //Recebimento
                String statusSuffix = "";
                if (transaction.getStatus().equals(TransactionStatus.PENDING)) {
                    statusSuffix = " (Agendada)";
                } else if (transaction.getStatus().equals(TransactionStatus.CANCELLED)) {
                    statusSuffix = " (Cancelada)";
                }
                
                transactionResponses.add(new TransactionResponse(
                        transaction.getSender().getFullName(),
                        transaction.getTransactionHash(),
                        transaction.getDescription() + statusSuffix,
                        transaction.getAmount(),
                        TransactionDirection.INCOMING,
                        transaction.getTargetDate().toString(),
                        TransactionTypes.TRANSFER,
                        transaction.getStatus()));
            }
        }

        return transactionResponses;
    }

    public void cancelTransaction(User user, Long transactionId, String reason) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new AppError("Transação não encontrada", HttpStatus.NOT_FOUND));
        
        if (!transaction.getStatus().equals(TransactionStatus.PENDING)) {
            throw new AppError("Apenas transações pendentes podem ser canceladas", HttpStatus.BAD_REQUEST);
        }

        if(transaction.getSender() != user){
            throw new AppError("Você não pode cancelar uma transação que não foi enviada por você.", HttpStatus.FORBIDDEN);
        }
        
        String originalDescription = transaction.getDescription();
        transaction.setDescription(originalDescription + " - " + reason);
        transaction.setStatus(TransactionStatus.CANCELLED);
        
        transactionRepository.save(transaction);
    }

    public List<AdminTransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        List<AdminTransactionResponse> adminTransactionResponses = new ArrayList<>();

        for (Transaction transaction : transactions) {
            UserDto sender = new UserDto();
            UserDto recipient = new UserDto();
            AdminTransactionResponse adminTransactionResponse = new AdminTransactionResponse();

            adminTransactionResponse.setAmount(transaction.getAmount());
            adminTransactionResponse.setDescription(transaction.getDescription());
            adminTransactionResponse.setTaxes(transaction.getTaxes());
            adminTransactionResponse.setTargetDate(transaction.getTargetDate());
            adminTransactionResponse.setTransactionHash(transaction.getTransactionHash());
            adminTransactionResponse.setStatus(transaction.getStatus());

            if (transaction.getSender() != null) {
                sender.setId(transaction.getSender().getId());
                sender.setFullName(transaction.getSender().getFullName());
                sender.setAccountNumber(transaction.getSender().getAccountNumber());
                adminTransactionResponse.setSender(sender);
            }

            if (transaction.getRecipient() != null) {
                recipient.setId(transaction.getRecipient().getId());
                recipient.setFullName(transaction.getRecipient().getFullName());
                recipient.setAccountNumber(transaction.getRecipient().getAccountNumber());
                adminTransactionResponse.setRecipient(recipient);
            }

            adminTransactionResponses.add(adminTransactionResponse);
        }

        return adminTransactionResponses;
    }

}
