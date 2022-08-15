package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper.impl;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper.BankAccountCreationUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import java.time.LocalDate;
import java.util.UUID;

public class BankAccountCreationUseCaseMapperImpl implements BankAccountCreationUseCaseMapper {

  public BankAccount toBankAccount(
      final BankAccountCreationRequest request) {
    final var bankAccountId = UUID.randomUUID();
    final var openIntervalDate = LocalDate.now();

    return BankAccount.initAccountWith(request.accountAlias(), request.accountCurrency(),
        bankAccountId, request.accountCustomerCode(), openIntervalDate);
  }

  public BankAccountCreationResponse toBankAccountCreateResponse(final BankAccount bankAccount) {

    final var accountBalance = bankAccount.accountBalance().amount().doubleValue();
    return new BankAccountCreationResponse(bankAccount.accountNumber().number(),
        accountBalance, bankAccount.aliasAccount(),
        bankAccount.defaultCurrency(), bankAccount.accountCustomer().id());
  }
}
