package com.joaodddev.bankapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String agency;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}