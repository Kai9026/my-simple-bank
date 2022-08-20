package com.github.kai9026.mysimplebank.infrastructure.web.mapper.moneytransfer;

import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer.model.MoneyTransferApiRequest;

public interface MoneyTransferApiMapper {

  TransferMoneyRequest toApplicationModel(MoneyTransferApiRequest request);
}
