package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model;

import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.time.LocalDate;
import java.util.UUID;

public record BankAccountDetailRequest(UUID bankAccountCode, LocalDate transactionsFrom,
                                       LocalDate transactionsTo) {

  public BankAccountDetailRequest {
    this.validateRequestParameters(bankAccountCode, transactionsFrom, transactionsTo);
  }

  private void validateRequestParameters(UUID bankAccountCode, LocalDate transactionsFrom,
      LocalDate transactionsTo) {
    if (isNull(bankAccountCode)) {
      throw new InvalidInputDataException("Account code is required");
    }
    if (isNull(transactionsFrom) && isNull(transactionsTo)) {
      throw new InvalidInputDataException("Dates are required");
    }
    if (transactionsTo.isBefore(transactionsFrom)) {
      throw new InvalidInputDataException("'To' param cannot be earlier than 'From' param");
    }
  }
}
