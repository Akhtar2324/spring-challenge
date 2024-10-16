package com.dws.challenge.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dws.challenge.domain.AccountTransaction;

/**
 * TODO: in db store tranaction history
 */
@Repository
public class TransactionHistoryRepository {

    private final List<AccountTransaction> transactions = new ArrayList<>();

    public void save(AccountTransaction transaction) {
        transactions.add(transaction);
    }

    public void clearHistory() {
        transactions.clear();
    }
}
