package com.bank.evolve.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.bank.evolve.dto.Request.AuthRequest;
import com.bank.evolve.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthRequest request) {
        String token = userService.authenticate(request.getEmail(), request.getPassword());
        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
