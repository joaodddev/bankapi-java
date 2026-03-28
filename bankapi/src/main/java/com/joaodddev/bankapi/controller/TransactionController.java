package com.joaodddev.bankapi.controller;

import com.joaodddev.bankapi.dto.request.TransferRequest;
import com.joaodddev.bankapi.dto.response.TransactionResponse;
import com.joaodddev.bankapi.model.User;
import com.joaodddev.bankapi.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(
            @AuthenticationPrincipal User user,
            @RequestParam BigDecimal amount) {
        transactionService.deposit(user, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TransferRequest request) {
        transactionService.transfer(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponse>> getHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(user));
    }
}