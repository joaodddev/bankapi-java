package com.joaodddev.bankapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String cpf;
    private String phone;
    private LocalDateTime createdAt;
    private AccountResponse account;
}