package com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountTransactionEntity;

public interface BankAccountTransactionMapper {

  BankAccountTransactionEntity toBankAccountTransactionEntity(
      BankAccountTransaction bankAccountTransaction);

  BankAccountTransaction toBankAccountTransaction(
      BankAccountTransactionEntity bankAccountTransactionEntity);
}
