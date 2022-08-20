package com.github.kai9026.mysimplebank.infrastructure.web.mapper.moneytransfer.impl;

import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer.model.MoneyTransferApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.moneytransfer.MoneyTransferApiMapper;

public class MoneyTransferApiMapperImpl implements MoneyTransferApiMapper {

  @Override
  public TransferMoneyRequest toApplicationModel(MoneyTransferApiRequest request) {
    return new TransferMoneyRequest(request.sourceAccount(), request.targetAccount(),
        request.transferAmount(), request.transferCurrency(), request.transferConcept());
  }
}
