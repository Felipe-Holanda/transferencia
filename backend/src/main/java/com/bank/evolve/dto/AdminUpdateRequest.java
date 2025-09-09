package com.bank.evolve.dto;

import com.bank.evolve.enums.Roles;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AdminUpdateRequest {

    private String name;

    @Email(message = "O email informado é inválido")
    private String email;

    private String phone;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,}$", message = "Sua senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial (@, #, $, %, &, *)")
    private String password;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    private boolean isActive;

    private Roles role;
}
