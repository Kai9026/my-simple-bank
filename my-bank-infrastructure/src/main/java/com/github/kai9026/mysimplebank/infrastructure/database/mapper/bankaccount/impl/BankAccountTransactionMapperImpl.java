package com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.impl;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.Money;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountTransactionEntity;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountTransactionMapper;
import java.util.UUID;

public class BankAccountTransactionMapperImpl implements BankAccountTransactionMapper {

  @Override
  public BankAccountTransactionEntity toBankAccountTransactionEntity(
      final BankAccountTransaction bankAccountTransaction, final UUID discriminatorAccountCode) {
    final var bankAccountTransactionEntity = new BankAccountTransactionEntity();
    bankAccountTransactionEntity.setTransactionCode(bankAccountTransaction.id().id());
    bankAccountTransactionEntity.setDiscriminatorAccountCode(discriminatorAccountCode);
    bankAccountTransactionEntity.setOriginAccountCode(
        bankAccountTransaction.originAccountCode().id());
    bankAccountTransactionEntity.setTargetAccountCode(
        bankAccountTransaction.targetAccountCode().id());
    bankAccountTransactionEntity.setAmount(
        bankAccountTransaction.transactionAmount().amount().floatValue());
    bankAccountTransactionEntity.setTransactionDate(bankAccountTransaction.transactionDate());
    bankAccountTransactionEntity.setConcept(bankAccountTransaction.concept());

    return bankAccountTransactionEntity;
  }

  @Override
  public BankAccountTransaction toBankAccountTransaction(
      final BankAccountTransactionEntity bankAccountTransactionEntity) {
    final var originAccountCode =
        BankAccountId.fromId(bankAccountTransactionEntity.getOriginAccountCode());
    final var targetAccountCode =
        BankAccountId.fromId(bankAccountTransactionEntity.getTargetAccountCode());
    final var amount = Money.of(bankAccountTransactionEntity.getAmount().doubleValue(), "EUR");

    return BankAccountTransaction.createTransactionWith(
        bankAccountTransactionEntity.getTransactionCode(),
        originAccountCode, targetAccountCode,
        amount, bankAccountTransactionEntity.getConcept(), false);
  }
}
