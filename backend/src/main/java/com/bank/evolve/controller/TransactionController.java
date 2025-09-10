package com.bank.evolve.controller;

import com.bank.evolve.entity.User;
import com.bank.evolve.dto.request.CalculateTaxesRequest;
import com.bank.evolve.dto.request.DepositRequest;
import com.bank.evolve.dto.request.TransactionRequest;
import com.bank.evolve.entity.Transaction;
import com.bank.evolve.service.TransactionService;
import com.bank.evolve.service.TransferTaxesService;
import com.bank.evolve.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    TransactionService transactionService;
    UserService userService;
    TransferTaxesService transferTaxesService;

    public TransactionController(TransactionService transactionService, UserService userService, TransferTaxesService transferTaxesService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transferTaxesService = transferTaxesService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculateTaxes(@RequestBody @Valid CalculateTaxesRequest calculateTaxesRequest){
        long days = ChronoUnit.DAYS.between(LocalDate.now(), calculateTaxesRequest.getTargetDate());
        Double calculatedTax = transferTaxesService.calculateTax(calculateTaxesRequest.getAmount(), days);
        HashMap<String, Double> response = new HashMap<>();
        response.put("calculatedTax", calculatedTax);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(@NotNull Authentication authentication, @RequestBody @Valid TransactionRequest transactionRequest) {
        String email = authentication.getName();
        User foundUser = userService.findByEmail(email);
        User targetUser = userService.findByAccountNumber(transactionRequest.getTargetAccount());

        Transaction transactionResultant = transactionService.createTransaction(foundUser, targetUser, transactionRequest);

        return new ResponseEntity<>(transactionResultant, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> updateTransaction(@NotNull Authentication authentication, @RequestBody @Valid DepositRequest transactionRequest) {
        String email = authentication.getName();
        User foundUser = userService.findByEmail(email);

        Transaction transactionResultant = transactionService.deposit(foundUser, transactionRequest.getAmount());
        return new ResponseEntity<>(transactionResultant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTransactions(@NotNull Authentication authentication) {
        String email = authentication.getName();
        User foundUser = userService.findByEmail(email);

        return new ResponseEntity<>(transactionService.getTransactionsByUser(foundUser), HttpStatus.OK);
    }

}
