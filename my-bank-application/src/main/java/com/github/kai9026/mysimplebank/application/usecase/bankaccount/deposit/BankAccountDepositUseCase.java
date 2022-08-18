package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;

public interface BankAccountDepositUseCase {

  BankAccountBaseResponse depositIntoAccount(BankAccountDepositRequest request);
}
