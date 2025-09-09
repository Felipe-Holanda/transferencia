package com.bank.evolve.service.impl;

import com.bank.evolve.dto.RegisterRequest;
import com.bank.evolve.dto.UpdateRequest;
import com.bank.evolve.entity.User;
import com.bank.evolve.repository.UserRepository;
import com.bank.evolve.helper.CpfHelper;
import com.bank.evolve.service.UserService;
import com.bank.evolve.util.HashUtil;
import com.bank.evolve.util.AppError;
import com.bank.evolve.config.JwtUtil;

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
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
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

        return userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND));
        if (!foundUser.getIsActive()) {
            throw new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        return foundUser;
    }

    public User findByAccountNumber(String accountNumber) {
        User foundUser = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND));
        if (!foundUser.getIsActive()) {
            throw new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        return foundUser;
    }

    public User updateUser(Long id, UpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND));

        if(!existingUser.getIsActive()){
            throw new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        if(request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())){
            if(userRepository.findByEmail(request.getEmail()).isPresent()){
                throw new AppError("Este e-mail já está cadastrado", HttpStatus.CONFLICT);
            }
            existingUser.setEmail(request.getEmail());
        }

        if (request.getPhone() != null) {
            existingUser.setPhone(request.getPhone());
        }

        if (request.getPassword() != null) {
            existingUser.setPasswordHash(HashUtil.hashPassword(request.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void softDeleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND));
        existingUser.setIsActive(false);
        userRepository.save(existingUser);
    }

    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppError("Credenciais inválidas", HttpStatus.UNAUTHORIZED));

        if (!user.getIsActive() || !HashUtil.checkPassword(password, user.getPasswordHash())) {
            throw new AppError("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return token;
    }

    @Override
    public User loadUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND));
        if (!foundUser.getIsActive()) {
            throw new AppError("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        return foundUser;
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
