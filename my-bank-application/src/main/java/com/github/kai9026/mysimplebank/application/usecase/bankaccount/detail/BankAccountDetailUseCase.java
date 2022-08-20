package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailResponse;

public interface BankAccountDetailUseCase {

  BankAccountDetailResponse getBankAccountDetail(BankAccountDetailRequest request);
}
