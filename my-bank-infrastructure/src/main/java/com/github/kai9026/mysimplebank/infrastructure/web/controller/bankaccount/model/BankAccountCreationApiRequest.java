package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;

public record BankAccountCreationApiRequest(@NotNull String alias, @NotNull String currency,
                                            @NotNull UUID customerCode) {

}
