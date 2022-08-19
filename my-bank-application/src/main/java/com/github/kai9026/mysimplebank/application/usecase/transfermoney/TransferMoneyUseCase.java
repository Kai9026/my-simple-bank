package com.github.kai9026.mysimplebank.application.usecase.transfermoney;

import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;

public interface TransferMoneyUseCase {

  void transferMoney(TransferMoneyRequest request);

}
