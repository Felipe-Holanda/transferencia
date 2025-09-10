package com.bank.evolve.service;

import com.bank.evolve.dto.request.AdminUpdateRequest;
import com.bank.evolve.dto.request.RegisterRequest;
import com.bank.evolve.dto.request.UpdateRequest;
import com.bank.evolve.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(RegisterRequest request);

    User loadUserById(Long id);

    User findByEmail(String email);

    User findByAccountNumber(String accountNumber);

    User updateUser(Long id, UpdateRequest request);

    void softDeleteUser(Long id);

    String authenticate(String email, String password);

    User adminFindById(Long id);

    User adminUpdate(Long id, AdminUpdateRequest request);

    User blockUnblockUser(Long id);

    void hardDeleteUser(Long id);
}
