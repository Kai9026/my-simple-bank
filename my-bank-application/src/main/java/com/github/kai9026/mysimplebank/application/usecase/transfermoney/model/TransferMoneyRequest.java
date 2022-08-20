package com.github.kai9026.mysimplebank.application.usecase.transfermoney.model;

import java.util.UUID;

public record TransferMoneyRequest(UUID sourceAccount, UUID targetAccount, double transferAmount,
                                   String transferCurrency, String transferConcept) {

}
