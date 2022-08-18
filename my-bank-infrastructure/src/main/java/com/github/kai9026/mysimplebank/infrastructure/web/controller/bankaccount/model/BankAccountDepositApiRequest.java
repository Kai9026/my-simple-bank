package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model;

import javax.validation.constraints.NotNull;

public record BankAccountDepositApiRequest(@NotNull double depositAmount, @NotNull String currency,
                                           String description) {

}
