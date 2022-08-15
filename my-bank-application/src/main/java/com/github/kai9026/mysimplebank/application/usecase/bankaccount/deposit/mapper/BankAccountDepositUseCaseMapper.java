package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;

public interface BankAccountDepositUseCaseMapper {

  BankAccountDepositResponse toBankAccountDepositResponse(BankAccount bankAccount);
}
