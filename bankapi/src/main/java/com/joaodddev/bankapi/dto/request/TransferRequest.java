package com.joaodddev.bankapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    @NotBlank(message = "Número da conta destino é obrigatório")
    private String destinationAccountNumber;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @DecimalMin(value = "0.01", message = "Valor mínimo de transferência é R$ 0,01")
    private BigDecimal amount;

    private String description;
}