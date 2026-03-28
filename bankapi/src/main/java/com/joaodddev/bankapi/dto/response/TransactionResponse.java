package com.joaodddev.bankapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private String type;
    private BigDecimal amount;
    private String description;
    private String destinationAccountNumber;
    private BigDecimal balanceAfter;
    private LocalDateTime transactionDate;
}