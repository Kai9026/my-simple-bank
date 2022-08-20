package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionDetail(UUID sourceAccount, UUID targetAccount, float amount,
                                String concept, LocalDate transactionDate) {

}
