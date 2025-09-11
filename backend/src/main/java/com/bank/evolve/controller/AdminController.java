package com.bank.evolve.controller;

import com.bank.evolve.dto.request.AdminUpdateRequest;
import com.bank.evolve.dto.request.TransferTaxesRequest;
import com.bank.evolve.dto.request.TransferTaxesUpdateRequest;
import com.bank.evolve.dto.response.AdminTransactionResponse;
import com.bank.evolve.dto.response.TransactionResponse;
import com.bank.evolve.entity.TransferTaxes;
import com.bank.evolve.service.TransactionService;
import com.bank.evolve.service.TransferTaxesService;
import com.bank.evolve.service.UserService;
import com.bank.evolve.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    UserService userService;
    TransferTaxesService transferTaxesService;
    TransactionService transactionService;

    public AdminController(UserService userService, TransferTaxesService transferTaxesService, TransactionService transactionService) {
        this.userService = userService;
        this.transferTaxesService = transferTaxesService;
        this.transactionService = transactionService;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@  User Management Endpoints @@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    @GetMapping("/user")
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userService.adminFindAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        User foundUser = userService.adminFindById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PatchMapping("/user/id/{id}")
    public ResponseEntity<Object> editUser(@PathVariable Long id, @RequestBody @Valid AdminUpdateRequest request) {
        User updatedUser = userService.adminUpdate(id, request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/user/id/{id}")
    public ResponseEntity<Object> blockUnblockUser(@PathVariable Long id) {
        User user = userService.blockUnblockUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/id/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user/id/{id}/hard")
    public ResponseEntity<Object> hardDeleteUser(@PathVariable Long id) {
        userService.hardDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@  Transfer Taxes Endpoints  @@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    @PostMapping("/taxes")
    public ResponseEntity<Object> setTransferTaxes(@RequestBody @Valid TransferTaxesRequest request) {
        TransferTaxes newTax = transferTaxesService.createNew(request);
        return new ResponseEntity<>(newTax, HttpStatus.CREATED);
    }

    @GetMapping("/taxes")
    public ResponseEntity<Object> getCurrentTransferTaxes() {
        List<TransferTaxes> currentTax = transferTaxesService.findAll();
        return new ResponseEntity<>(currentTax, HttpStatus.OK);
    }

    @PatchMapping("/taxes/id/{id}")
    public ResponseEntity<Object> updateTransferTaxes(@PathVariable Long id, @RequestBody @Valid TransferTaxesUpdateRequest request) {
        TransferTaxes updatedTax = transferTaxesService.update(id, request);
        return new ResponseEntity<>(updatedTax, HttpStatus.OK);
    }

    @DeleteMapping("/taxes/id/{id}")
    public ResponseEntity<Object> deleteTransferTaxes(@PathVariable Long id) {
        transferTaxesService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@  Additional Admin Endpoints @@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    @GetMapping("/transactions")
    public ResponseEntity<Object> listAllTransactions() {
        List<AdminTransactionResponse> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/id/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Long id) {
        User foundUser = userService.adminFindById(id);
        List<TransactionResponse> transactions = transactionService.getTransactionsByUser(foundUser);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}