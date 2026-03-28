package com.joaodddev.bankapi.service;

import com.joaodddev.bankapi.dto.response.AccountResponse;
import com.joaodddev.bankapi.exception.BusinessException;
import com.joaodddev.bankapi.model.Account;
import com.joaodddev.bankapi.model.User;
import com.joaodddev.bankapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse getAccountByUser(User user) {
        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));

        return mapToAccountResponse(account);
    }

    public Account getAccountEntityByUser(User user) {
        return accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Conta destino não encontrada"));
    }

    public void validateBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Saldo insuficiente. Saldo atual: R$ " + account.getBalance());
        }
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .agency(account.getAgency())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .build();
    }
}