package com.bank.evolve.service;

import com.bank.evolve.dto.RegisterRequest;
import com.bank.evolve.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(RegisterRequest request);

    User loadUserById(Long id);
}
