package com.joaodddev.bankapi.service;

import com.joaodddev.bankapi.dto.request.UserRequest;
import com.joaodddev.bankapi.dto.response.UserResponse;
import com.joaodddev.bankapi.exception.BusinessException;
import com.joaodddev.bankapi.model.Account;
import com.joaodddev.bankapi.model.User;
import com.joaodddev.bankapi.repository.AccountRepository;
import com.joaodddev.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (userRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setCpf(request.getCpf());
        user.setPhone(request.getPhone());
        user.setActive(true);

        User savedUser = userRepository.save(user);

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setUser(savedUser);

        accountRepository.save(account);

        return mapToUserResponse(savedUser);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = String.format("%08d", random.nextInt(100000000));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .cpf(user.getCpf())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .build();
    }
}