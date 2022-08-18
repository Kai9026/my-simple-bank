package com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;

public class BankAccountResourceMapperImpl implements BankAccountResourceMapper {

  @Override
  public BankAccountResource toBankAccountResource(BankAccountBaseResponse response) {
    return BankAccountResource.builder()
        .bankAccountCode(response.bankAccountCode().toString())
        .accountNumber(response.accountNumber())
        .accountBalance(response.accountBalance())
        .accountAlias(response.accountAlias())
        .accountCurrency(response.accountCurrency())
        .accountCustomerCode(response.accountCustomerCode().toString())
        .build();
  }
}
