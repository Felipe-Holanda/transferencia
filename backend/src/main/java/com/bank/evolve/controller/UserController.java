package com.bank.evolve.controller;

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

}
