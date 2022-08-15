package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositResponse;

public interface BankAccountDepositUseCase {

  BankAccountDepositResponse depositIntoAccount(BankAccountDepositRequest request);
}
