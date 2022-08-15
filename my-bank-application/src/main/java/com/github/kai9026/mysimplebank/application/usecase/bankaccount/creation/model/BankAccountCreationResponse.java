package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model;

import java.util.UUID;

public record BankAccountCreationResponse(String accountNumber, double accountBalance,
                                          String accountAlias, String accountCurrency,
                                          UUID accountCustomerCode) {

}
