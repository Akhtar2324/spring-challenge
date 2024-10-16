package com.dws.challenge.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.AccountTransaction;
import com.dws.challenge.repository.AccountsRepositoryInMemory;
import com.dws.challenge.repository.TransactionHistoryRepository;
import com.dws.challenge.request.dto.TransferRequest;

@Service
public class TransactionService {

    private final ReentrantLock lock = new ReentrantLock();

    private final AccountsRepositoryInMemory accountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionService(AccountsRepositoryInMemory accountRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.accountRepository = accountRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    /**
     * TODO: @Transaction ensures that the operation adheres to the ACID principles
     * Transfer fund from one account to another
     */
    public void transfer(TransferRequest request) throws IllegalArgumentException {
        Optional<Account> accountFromOpt = this.accountRepository.getAccount(request.getAccountFromId());
        Optional<Account> accountToOpt = this.accountRepository.getAccount(request.getAccountToId());

        if (accountFromOpt.isPresent() && accountToOpt.isPresent()) {
            Account accountFrom = accountFromOpt.get();
            Account accountTo = accountToOpt.get();

            if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Transfer amount must be positive");
            }
            if (accountFrom.getBalance().compareTo(request.getAmount()) <= 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }

            // Critical section, to Perform transfer in thread safe mode
            lock.lock();
            try{
                // Step 1: transfer balance
                accountFrom.setBalance(accountFrom.getBalance().subtract(request.getAmount()));
                accountTo.setBalance(accountTo.getBalance().add(request.getAmount()));
    
                accountRepository.save(accountFrom);
                accountRepository.save(accountTo);

                // Step 2 to record the transaction history
                this.recordTransactionHistory(createTransferFromTransactionHistory(request));
                this.recordTransactionHistory(createTransferToTransactionHistory(request));
            }
            finally {
                lock.unlock();
            }
            
        } else {
            throw new IllegalArgumentException("Accounts are not found");
        }
    }

    private void recordTransactionHistory(AccountTransaction transaction) {
        // Save the transaction history to DB
        transactionHistoryRepository.save(transaction);
    }
    
    private AccountTransaction createTransferFromTransactionHistory(TransferRequest request) {
        AccountTransaction transaction = new AccountTransaction(request.getAccountFromId(),
            AccountTransaction.TransactionType.TRANSFER,
            LocalDateTime.now(),
            request.getAmount(),
            AccountTransaction.TransactionStatus.SUCCESS,
            "Amount is transferred to " + request.getAccountToId());
       
     return transaction;  
    }

    private AccountTransaction createTransferToTransactionHistory(TransferRequest request) {
        AccountTransaction transaction = new AccountTransaction(request.getAccountToId(),
            AccountTransaction.TransactionType.TRANSFER,
            LocalDateTime.now(),
            request.getAmount(),
            AccountTransaction.TransactionStatus.SUCCESS,
            "Amoount is Recieved from " + request.getAccountFromId());
       
     return transaction;  
    }

}
