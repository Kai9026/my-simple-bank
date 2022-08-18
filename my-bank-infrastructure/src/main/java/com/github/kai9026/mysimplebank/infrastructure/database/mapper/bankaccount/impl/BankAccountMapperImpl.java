package com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.impl;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountEntity;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountMapper;
import java.util.List;

public class BankAccountMapperImpl implements BankAccountMapper {

  @Override
  public BankAccountEntity toBankAccountEntity(BankAccount bankAccount) {
    final var bankAccountEntity = new BankAccountEntity();
    bankAccountEntity.setAccountCode(bankAccount.id().id());
    bankAccountEntity.setAlias(bankAccount.aliasAccount());
    bankAccountEntity.setAccountNumber(bankAccount.accountNumber().number());
    bankAccountEntity.setConsolidatedBalance(bankAccount.consolidatedBalance().amount().floatValue());
    bankAccountEntity.setIntervalBalance(bankAccount.intervalBalance().amount().floatValue());
    bankAccountEntity.setDefaultCurrency(bankAccount.defaultCurrency());
    bankAccountEntity.setStartInterval(bankAccount.openIntervalDate());
    bankAccountEntity.setEndInterval(bankAccount.closeIntervalDate());
    bankAccountEntity.setCustomerCode(bankAccount.accountCustomer().id());

    return bankAccountEntity;
  }

  @Override
  public BankAccount toBankAccount(BankAccountEntity bankAccountEntity,
      List<BankAccountTransaction> transactions) {
    return BankAccount.initAccountWithTransactions(bankAccountEntity.getAlias(),
        bankAccountEntity.getAccountNumber(), bankAccountEntity.getDefaultCurrency(),
        bankAccountEntity.getAccountCode(), bankAccountEntity.getCustomerCode(),
        bankAccountEntity.getStartInterval(),
        bankAccountEntity.getConsolidatedBalance(), transactions);
  }
}
