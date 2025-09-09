package com.bank.evolve.service;

import com.bank.evolve.dto.RegisterRequest;
import com.bank.evolve.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(RegisterRequest request);

    User loadUserById(Long id);

    User findByEmail(String email);

    User findByAccountNumber(String accountNumber);

    User updateUser(Long id, RegisterRequest request);

    void softDeleteUser(Long id);
}
