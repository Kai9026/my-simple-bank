package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.util.UUID;

public record BankAccountCreationRequest(String accountAlias, String accountCurrency,
                                         UUID accountCustomerCode) {

  public BankAccountCreationRequest {
    this.validateRequestRequiredFields(accountAlias, accountCurrency, accountCustomerCode);

  }

  private void validateRequestRequiredFields(String accountAlias, String accountCurrency,
      UUID accountCustomerCode) {
    if (isEmptyField(accountAlias)) {
      throw new InvalidInputDataException("Account alias is required");
    }
    if (isEmptyField(accountCurrency)) {
      throw new InvalidInputDataException("Account currency is required");
    }
    if (isNull(accountCustomerCode)) {
      throw new InvalidInputDataException("Account customer code is required");
    }
  }

}