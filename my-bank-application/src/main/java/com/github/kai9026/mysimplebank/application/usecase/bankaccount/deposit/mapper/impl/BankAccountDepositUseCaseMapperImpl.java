package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.impl;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.BankAccountDepositUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;

public class BankAccountDepositUseCaseMapperImpl implements BankAccountDepositUseCaseMapper {

  @Override
  public BankAccountBaseResponse toBankAccountBaseResponse(final BankAccount bankAccount) {

    final var accountNumber = bankAccount.accountNumber().number();
    final var accountBalance = bankAccount.accountBalance().amount().doubleValue();
    final var bankAccountCode = bankAccount.id().id();

    return new BankAccountBaseResponse(bankAccountCode, accountNumber, accountBalance,
        bankAccount.aliasAccount(), bankAccount.defaultCurrency(),
        bankAccount.accountCustomer().id());
  }
}
