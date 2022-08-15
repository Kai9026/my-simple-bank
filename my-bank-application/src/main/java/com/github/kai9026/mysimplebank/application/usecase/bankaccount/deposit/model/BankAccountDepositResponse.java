package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model;

import java.util.UUID;

public record BankAccountDepositResponse(String accountNumber, double accountBalance,
                                         String accountAlias, String accountCurrency,
                                         UUID accountCustomerCode) {

}
