package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.mapper.impl;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.mapper.BankAccountDetailMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailResponse;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.TransactionDetail;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import java.util.List;

public class BankAccountDetailMapperImpl implements BankAccountDetailMapper {

  @Override
  public BankAccountDetailResponse toBankAccountDetailResponse(final float balance,
      final List<BankAccountTransaction> transactions) {

    final var transactionDetails = transactions.stream()
        .map(tx -> new TransactionDetail(tx.originAccountCode().id(), tx.targetAccountCode().id(),
            tx.transactionAmount().amount().floatValue(), tx.concept(), tx.transactionDate()))
        .toList();
    return new BankAccountDetailResponse(balance, transactionDetails);
  }
}
