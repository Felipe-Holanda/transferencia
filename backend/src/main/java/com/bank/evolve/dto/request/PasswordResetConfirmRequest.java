package com.bank.evolve.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PasswordResetConfirmRequest {
    
    @NotBlank(message = "Código OTP é obrigatório")
    @Pattern(regexp = "\\d{6}", message = "Código OTP deve conter exatamente 6 dígitos")
    private String token;
    
    @NotBlank(message = "Nova senha é obrigatória")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,}$", 
             message = "A senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial (@, #, $, %, &, *)")
    private String newPassword;
}
