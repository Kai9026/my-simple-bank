package com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountEntity;
import java.util.List;

public interface BankAccountMapper {

  BankAccountEntity toBankAccountEntity(BankAccount bankAccount);

  BankAccount toBankAccount(BankAccountEntity bankAccountEntity,
      List<BankAccountTransaction> transactions);
}
