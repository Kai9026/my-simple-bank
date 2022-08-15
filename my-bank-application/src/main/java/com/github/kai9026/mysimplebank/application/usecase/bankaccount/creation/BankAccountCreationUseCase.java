package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation;


import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationResponse;

public interface BankAccountCreationUseCase {

  BankAccountCreationResponse createNewBankAccount(BankAccountCreationRequest request);
}
