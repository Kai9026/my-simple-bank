package com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.impl;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.BankAccountApiMapper;

public class BankAccountApiMapperImpl implements BankAccountApiMapper {

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
