package com.joaodddev.bankapi.service;

import com.joaodddev.bankapi.dto.request.TransferRequest;
import com.joaodddev.bankapi.dto.response.TransactionResponse;
import com.joaodddev.bankapi.exception.BusinessException;
import com.joaodddev.bankapi.model.Account;
import com.joaodddev.bankapi.model.Transaction;
import com.joaodddev.bankapi.model.User;
import com.joaodddev.bankapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Transactional
    public void deposit(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor do depósito deve ser maior que zero");
        }

        Account account = accountService.getAccountEntityByUser(user);
        account.setBalance(account.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setDescription("Depósito em conta");
        transaction.setBalanceAfter(account.getBalance());

        transactionRepository.save(transaction);
    }

    @Transactional
    public void transfer(User user, TransferRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor de transferência deve ser maior que zero");
        }

        Account sourceAccount = accountService.getAccountEntityByUser(user);
        accountService.validateBalance(sourceAccount, request.getAmount());

        Account destinationAccount = accountService.getAccountByNumber(request.getDestinationAccountNumber());

        if (sourceAccount.getId().equals(destinationAccount.getId())) {
            throw new BusinessException("Não é possível transferir para a mesma conta");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));

        Transaction sentTransaction = new Transaction();
        sentTransaction.setAccount(sourceAccount);
        sentTransaction.setType("TRANSFER_SENT");
        sentTransaction.setAmount(request.getAmount());
        sentTransaction
                .setDescription(request.getDescription() != null ? request.getDescription() : "Transferência enviada");
        sentTransaction.setDestinationAccountNumber(destinationAccount.getAccountNumber());
        sentTransaction.setBalanceAfter(sourceAccount.getBalance());

        Transaction receivedTransaction = new Transaction();
        receivedTransaction.setAccount(destinationAccount);
        receivedTransaction.setType("TRANSFER_RECEIVED");
        receivedTransaction.setAmount(request.getAmount());
        receivedTransaction
                .setDescription(request.getDescription() != null ? request.getDescription() : "Transferência recebida");
        receivedTransaction.setDestinationAccountNumber(sourceAccount.getAccountNumber());
        receivedTransaction.setBalanceAfter(destinationAccount.getBalance());

        transactionRepository.save(sentTransaction);
        transactionRepository.save(receivedTransaction);
    }

    public List<TransactionResponse> getTransactionHistory(User user) {
        Account account = accountService.getAccountEntityByUser(user);
        List<Transaction> transactions = transactionRepository.findByAccountOrderByTransactionDateDesc(account);

        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .destinationAccountNumber(transaction.getDestinationAccountNumber())
                .balanceAfter(transaction.getBalanceAfter())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }
}