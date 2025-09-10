package com.bank.evolve.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.bank.evolve.dto.request.AuthRequest;
import com.bank.evolve.dto.request.PasswordResetConfirmRequest;
import com.bank.evolve.dto.request.PasswordResetRequest;
import com.bank.evolve.service.PasswordResetService;
import com.bank.evolve.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserService userService;
    PasswordResetService passwordResetService;

    public AuthController(UserService userService, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }
    
    @PostMapping
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthRequest request) {
        String token = userService.authenticate(request.getEmail(), request.getPassword());
        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        passwordResetService.requestPasswordReset(request.getEmail());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Se o email estiver cadastrado, você receberá um código de verificação em instantes.");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<Map<String, String>> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha alterada com sucesso!");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
