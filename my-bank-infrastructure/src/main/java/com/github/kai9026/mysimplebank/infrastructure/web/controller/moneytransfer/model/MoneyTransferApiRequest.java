package com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;

public record MoneyTransferApiRequest(@NotNull UUID sourceAccount,
                                      @NotNull UUID targetAccount,
                                      @NotNull double transferAmount,
                                      @NotNull String transferCurrency,
                                      String transferConcept) {


}
