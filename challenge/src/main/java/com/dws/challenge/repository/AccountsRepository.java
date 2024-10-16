package com.dws.challenge.repository;

import java.util.Optional;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Optional<Account> getAccount(String accountId);

  void save(Account account);

  void clearAccounts();
}
