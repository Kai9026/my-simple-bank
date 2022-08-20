package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.mapper;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import java.util.List;

public interface BankAccountDetailMapper {

  BankAccountDetailResponse toBankAccountDetailResponse(float account,
      List<BankAccountTransaction> transactions);
}
