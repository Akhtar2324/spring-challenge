package com.dws.challenge.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * TODO: Record the each transaction history
 */
@Data
public class AccountTransaction {
    
  private final String accountId;
  private TransactionType transactionType;
  private LocalDateTime transactionDate;
  private BigDecimal amount;
  private TransactionStatus status;
  private String description;

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    public enum TransactionStatus {
        SUCCESS, FAILED, PENDING
    }

    public AccountTransaction(String accountId, TransactionType transactionType, LocalDateTime transactionDate,
            BigDecimal amount, TransactionStatus status, String description) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.status = status;
        this.description = description;
    }

    
}
