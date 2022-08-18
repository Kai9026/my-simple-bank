package com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;

public interface BankAccountResourceMapper {

  BankAccountResource toBankAccountResource(BankAccountBaseResponse response);
}
