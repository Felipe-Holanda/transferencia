package com.bank.evolve.entity;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column
    private Double balance = 0.0;

    @Column(nullable = false)
    private Boolean isActive = true;

}
