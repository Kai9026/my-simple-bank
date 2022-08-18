package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;

public interface BankAccountCreationUseCaseMapper {

  BankAccount toBankAccount(BankAccountCreationRequest request);

  BankAccountBaseResponse toBankAccountBaseResponse(BankAccount bankAccount);
}
