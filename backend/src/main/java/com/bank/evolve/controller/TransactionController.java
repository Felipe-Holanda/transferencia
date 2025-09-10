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
        Long days = (calculateTaxesRequest.getTargetDate().getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
        Double calculatedTax = transferTaxesService.calculateTax(calculateTaxesRequest.getAmount(), days);
        return new ResponseEntity<>(calculatedTax, HttpStatus.OK);
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
