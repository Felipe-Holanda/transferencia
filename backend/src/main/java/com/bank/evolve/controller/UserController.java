package com.bank.evolve.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import com.bank.evolve.service.UserService;
import com.bank.evolve.dto.RegisterRequest;
import com.bank.evolve.entity.User;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    @PostMapping()
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request) {
        User createdUser = userService.createUser(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(@NonNull Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Object> getUserByAccountNumber(@PathVariable String accountNumber) {
        User user = userService.findByAccountNumber(accountNumber);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/me")
    public ResponseEntity<Object> updateCurrentUser(@NonNull Authentication authentication, @RequestBody @Valid RegisterRequest request) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        // Atualize os campos permitidos
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        User updatedUser = userService.updateUser(user.getId(), request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Object> deleteCurrentUser(@NonNull Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        userService.softDeleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}