package com.bank.evolve.controller;

import com.bank.evolve.dto.request.PasswordResetConfirmRequest;
import com.bank.evolve.dto.request.PasswordResetRequest;
import com.bank.evolve.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        passwordResetService.requestPasswordReset(request.getEmail());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Se o email estiver cadastrado, você receberá um código de verificação em instantes.");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, String>> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha alterada com sucesso!");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
