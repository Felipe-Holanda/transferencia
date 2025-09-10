package com.bank.evolve.dto.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Você deve preencher o nome completo")
    private String fullName;

    @Email(message = "Você deve preencher um email válido")
    @NotBlank(message = "Você deve informar o seu email")
    private String email;

    @NotBlank(message = "Você deve preencher o CPF")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    @NotBlank(message = "Você deve informar o seu telefone")
    private String phone;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,}$", message = "Sua senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial (@, #, $, %, &, *)")
    @NotBlank(message = "Você deve informar uma senha")
    private String password;
}