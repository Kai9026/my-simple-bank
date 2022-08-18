package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation;


import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;

public interface BankAccountCreationUseCase {

  BankAccountBaseResponse createNewBankAccount(BankAccountCreationRequest request);
}
