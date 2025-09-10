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
import com.bank.evolve.dto.request.RegisterRequest;
import com.bank.evolve.dto.request.UpdateRequest;
import com.bank.evolve.dto.response.UserResponse;
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

    @GetMapping()
    public ResponseEntity<Object> getCurrentUser(@NonNull Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Object> getUserByAccountNumber(@PathVariable String accountNumber) {
        User user = userService.findByAccountNumber(accountNumber);

        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getFullName());
        userResponse.setAccountNumber(user.getAccountNumber());

        String maskedCpf = user.getCpf().substring(0, 4) + "***.**" + user.getCpf().substring(10);
        userResponse.setCpf(maskedCpf);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<Object> updateCurrentUser(@NonNull Authentication authentication, @RequestBody @Valid UpdateRequest request) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        User updatedUser = userService.updateUser(user.getId(), request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteCurrentUser(@NonNull Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        userService.softDeleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}