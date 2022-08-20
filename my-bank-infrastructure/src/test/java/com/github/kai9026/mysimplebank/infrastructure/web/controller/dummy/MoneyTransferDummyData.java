package com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy;

import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer.model.MoneyTransferApiRequest;
import java.util.UUID;

public final class MoneyTransferDummyData {

  private MoneyTransferDummyData() {}

  public static MoneyTransferApiRequest createMoneyTransferRequest() {
    return new MoneyTransferApiRequest(UUID.randomUUID(), UUID.randomUUID(), 10.00,
        "EUR", "Transfer");
  }

  public static TransferMoneyRequest createTransferMoneyRequest() {
    return new TransferMoneyRequest(UUID.randomUUID(), UUID.randomUUID(), 10.00,
        "EUR", "Transfer");
  }
}
