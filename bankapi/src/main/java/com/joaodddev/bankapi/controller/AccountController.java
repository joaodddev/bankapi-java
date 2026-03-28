package com.joaodddev.bankapi.controller;

import com.joaodddev.bankapi.dto.response.AccountResponse;
import com.joaodddev.bankapi.model.User;
import com.joaodddev.bankapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getMyAccount(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getAccountByUser(user));
    }
}