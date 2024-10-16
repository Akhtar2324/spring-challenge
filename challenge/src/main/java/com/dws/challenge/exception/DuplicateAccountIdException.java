package com.dws.challenge.exception;

public class DuplicateAccountIdException extends AccountNotFoundException {

  public DuplicateAccountIdException(String message) {
    super(message);
  }
}
