package com.joaodddev.bankapi.service;

import com.joaodddev.bankapi.config.JwtService;
import com.joaodddev.bankapi.dto.request.LoginRequest;
import com.joaodddev.bankapi.dto.response.AuthResponse;
import com.joaodddev.bankapi.dto.response.UserResponse;
import com.joaodddev.bankapi.model.User;
import com.joaodddev.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .user(mapToUserResponse(user))
                .build();
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