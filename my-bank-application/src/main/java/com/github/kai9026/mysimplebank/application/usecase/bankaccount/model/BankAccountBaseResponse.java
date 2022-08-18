package com.github.kai9026.mysimplebank.application.usecase.bankaccount.model;

import java.util.UUID;

public record BankAccountBaseResponse(UUID bankAccountCode, String accountNumber, double accountBalance,
                                      String accountAlias, String accountCurrency,
                                      UUID accountCustomerCode) {

}
