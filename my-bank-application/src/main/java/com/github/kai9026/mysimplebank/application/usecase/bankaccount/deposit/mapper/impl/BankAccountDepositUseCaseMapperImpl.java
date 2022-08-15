package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.impl;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.BankAccountDepositUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;

public class BankAccountDepositUseCaseMapperImpl implements BankAccountDepositUseCaseMapper {

  @Override
  public BankAccountDepositResponse toBankAccountDepositResponse(final BankAccount bankAccount) {

    final var accountNumber = bankAccount.accountNumber().number();
    final var accountBalance = bankAccount.accountBalance().amount().doubleValue();

    return new BankAccountDepositResponse(accountNumber, accountBalance,
        bankAccount.aliasAccount(), bankAccount.defaultCurrency(),
        bankAccount.accountCustomer().id());
  }
}
