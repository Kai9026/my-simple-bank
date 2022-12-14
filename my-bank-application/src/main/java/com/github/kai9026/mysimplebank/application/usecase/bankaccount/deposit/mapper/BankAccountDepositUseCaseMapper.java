package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;

public interface BankAccountDepositUseCaseMapper {

  BankAccountBaseResponse toBankAccountBaseResponse(BankAccount bankAccount);
}
