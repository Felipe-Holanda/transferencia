package com.bank.evolve.dto.Request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthRequest {
    @Email(message = "Você deve preencher um email válido")
    @NotBlank(message = "Você deve informar o seu email")
    private String email;

    @NotBlank(message = "Você deve informar uma senha")
    private String password;
}
