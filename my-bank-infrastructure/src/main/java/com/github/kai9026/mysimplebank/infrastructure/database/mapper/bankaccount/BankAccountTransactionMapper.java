package com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountTransactionEntity;
import java.util.UUID;

public interface BankAccountTransactionMapper {

  BankAccountTransactionEntity toBankAccountTransactionEntity(
      BankAccountTransaction bankAccountTransaction, UUID discriminatorAccountCode);

  BankAccountTransaction toBankAccountTransaction(
      BankAccountTransactionEntity bankAccountTransactionEntity);
}
