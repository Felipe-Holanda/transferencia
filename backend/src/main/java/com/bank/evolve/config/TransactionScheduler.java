package com.bank.evolve.config;

import com.bank.evolve.entity.Transaction;
import com.bank.evolve.enums.TransactionStatus;
import com.bank.evolve.repository.TransactionRepository;
import com.bank.evolve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionScheduler {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 9 * * ?")
    @Transactional
    public void processScheduledTransactions() {
        log.info("Iniciando processamento de transações agendadas para hoje...");
        processTransactionsForDate(LocalDate.now());
    }

    @Transactional
    public void processTransactionsForDate(LocalDate date) {
        List<Transaction> pendingTransactions = transactionRepository
            .findByTargetDateAndStatus(date, TransactionStatus.PENDING);
        
        int successCount = 0;
        int failureCount = 0;
        
        for (Transaction transaction : pendingTransactions) {
            try {
                processTransaction(transaction);
                successCount++;
                log.info("Transação {} processada com sucesso", transaction.getTransactionHash());
            } catch (Exception e) {
                failureCount++;
                log.error("Erro ao processar transação {}: {}", 
                    transaction.getTransactionHash(), e.getMessage());
                transaction.setStatus(TransactionStatus.CANCELLED);
                transactionRepository.save(transaction);
            }
        }
        
        log.info("Processamento concluído para {}: {} sucessos, {} falhas, {} total", 
            date, successCount, failureCount, pendingTransactions.size());
    }

    private void processTransaction(Transaction transaction) {
        double totalAmount = transaction.getAmount() + transaction.getTaxes();
        
        if (transaction.getSender().getBalance() < totalAmount) {
            log.warn("Transação {} cancelada: saldo insuficiente", transaction.getTransactionHash());
            
            String originalDescription = transaction.getDescription();
            transaction.setDescription(originalDescription + " - Esta transação foi cancelada devido fundos insuficientes do remetente");
            transaction.setStatus(TransactionStatus.CANCELLED);
            
            transactionRepository.save(transaction);
            return;
        }
        
        transaction.getSender().setBalance(
            transaction.getSender().getBalance() - totalAmount);
        transaction.getRecipient().setBalance(
            transaction.getRecipient().getBalance() + transaction.getAmount());
        
        transaction.setStatus(TransactionStatus.COMPLETED);
        
        userRepository.save(transaction.getSender());
        userRepository.save(transaction.getRecipient());
        transactionRepository.save(transaction);
    }
}
