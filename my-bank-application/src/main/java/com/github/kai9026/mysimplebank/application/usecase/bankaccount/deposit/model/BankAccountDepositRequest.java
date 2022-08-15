package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.util.UUID;

public record BankAccountDepositRequest(double depositAmount, String depositCurrency,
                                        UUID bankAccountId, String depositDescription) {

  public BankAccountDepositRequest {
    this.validateRequiredRequestFields(depositAmount, depositCurrency, bankAccountId);

  }

  private void validateRequiredRequestFields(double depositAmount, String depositCurrency,
      UUID bankAccountId) {
    if (depositAmount < 0) {
      throw new InvalidInputDataException("Amount should be greater than 0");
    }
    if (isEmptyField(depositCurrency)) {
      throw new InvalidInputDataException("Currency is required");
    }
    if (isNull(bankAccountId)) {
      throw new InvalidInputDataException("Bank account code is required");
    }
  }
}
