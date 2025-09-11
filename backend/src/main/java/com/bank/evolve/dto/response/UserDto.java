package com.bank.evolve.dto.response;

import com.bank.evolve.enums.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String cpf;
    private String passwordHash;
    private String accountNumber;
    private Double balance;
    private Boolean isActive;
    private Roles role;

}
