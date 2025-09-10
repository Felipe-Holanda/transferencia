package com.bank.evolve.config;

import com.bank.evolve.entity.TransferTaxes;
import com.bank.evolve.repository.TransferTaxesRepository;
import com.bank.evolve.repository.UserRepository;
import com.bank.evolve.entity.User;
import com.bank.evolve.enums.Roles;
import com.bank.evolve.util.HashUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final TransferTaxesRepository transferTaxesRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        loadTransferTaxes();
        loadAdminUser();
    }

    private void loadTransferTaxes() {
        if (transferTaxesRepository.count() == 0) {
            log.info("Criando taxas de transferência padrão...");
            
            // Taxa para transferências imediatas (0 dias)
            TransferTaxes immediateTax = new TransferTaxes();
            immediateTax.setAmountDays(0);
            immediateTax.setFixedTax(3.00);
            immediateTax.setTaxPercentage(2.5);
            transferTaxesRepository.save(immediateTax);
            
            // Taxa para transferências até 10 dias
            TransferTaxes upTo10Days = new TransferTaxes();
            upTo10Days.setAmountDays(10);
            upTo10Days.setFixedTax(12.00);
            upTo10Days.setTaxPercentage(0.0);
            transferTaxesRepository.save(upTo10Days);
            
            // Taxa para transferências entre 11 e 20 dias
            TransferTaxes upTo20Days = new TransferTaxes();
            upTo20Days.setAmountDays(20);
            upTo20Days.setFixedTax(0.0);
            upTo20Days.setTaxPercentage(8.2);
            transferTaxesRepository.save(upTo20Days);
            
            // Taxa para transferências entre 21 e 30 dias
            TransferTaxes upTo30Days = new TransferTaxes();
            upTo30Days.setAmountDays(30);
            upTo30Days.setFixedTax(0.0);
            upTo30Days.setTaxPercentage(6.9);
            transferTaxesRepository.save(upTo30Days);
            
            // Taxa para transferências entre 31 e 40 dias
            TransferTaxes upTo40Days = new TransferTaxes();
            upTo40Days.setAmountDays(40);
            upTo40Days.setFixedTax(0.0);
            upTo40Days.setTaxPercentage(4.7);
            transferTaxesRepository.save(upTo40Days);
            
            // Taxa para transferências entre 41 a 50 dias
            TransferTaxes upTo50Days = new TransferTaxes();
            upTo50Days.setAmountDays(50);
            upTo50Days.setFixedTax(0.0);
            upTo50Days.setTaxPercentage(1.7);
            transferTaxesRepository.save(upTo50Days);
            
            log.info("Taxas de transferência padrão criadas com sucesso!");
        } else {
            log.info("Taxas de transferência já existem no banco de dados.");
        }
    }

    private void loadAdminUser() {
        if (userRepository.count() == 0) {
            log.info("Criando usuário admin padrão...");
            
            // Criação do usuário admin
            User adminUser = new User();
            adminUser.setFullName("Admin User");
            adminUser.setEmail("admin@example.com");
            adminUser.setCpf("000.000.000-00");
            adminUser.setPhone("(00) 0 0000-0000");
            adminUser.setAccountNumber(String.format("%010d", new Random().nextInt(1_000_000_000)));
            adminUser.setPasswordHash(HashUtil.hashPassword("@Admin123"));
            adminUser.setRole(Roles.ADMIN);
            userRepository.save(adminUser);
            log.info("Usuário admin padrão criado com sucesso!");
        } else {
            log.info("Usuário admin já existe no banco de dados.");
        }
    }
}
