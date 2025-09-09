package com.bank.evolve.service.impl;

import com.bank.evolve.dto.RegisterRequest;
import com.bank.evolve.entity.User;
import com.bank.evolve.repository.UserRepository;
import com.bank.evolve.helper.CpfHelper;
import com.bank.evolve.service.UserService;
import com.bank.evolve.util.HashUtil;
import com.bank.evolve.util.AppError;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Random random = new Random();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppError("Este e-mail já está cadastrado", HttpStatus.CONFLICT);
        }
        if (userRepository.findByCpf(request.getCpf()).isPresent()) {
            throw new AppError("Este CPF já está cadastrado", HttpStatus.CONFLICT);
        }
        if (!CpfHelper.isValidCPF(request.getCpf())) {
            throw new AppError("O CPF informado é inválido", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setCpf(request.getCpf());
        newUser.setPhone(request.getPhone());
        newUser.setPasswordHash(HashUtil.hashPassword(request.getPassword()));

        String accountNumber;
        do {
            accountNumber = String.format("%010d", random.nextInt(1_000_000_000));
        } while (userRepository.findByAccountNumber(accountNumber).isPresent());
        newUser.setAccountNumber(accountNumber);

        User createdUser = userRepository.save(newUser);
        createdUser.setPasswordHash(null);
        return createdUser;
    }

    @Override
    public User loadUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
