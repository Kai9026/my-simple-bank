package com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.impl;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountTransactionMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.BankAccountJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.BankAccountTransactionJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;

@RequiredArgsConstructor
@Slf4j
public class BankAccountRepositoryImpl implements BankAccountRepository {

  private final BankAccountJpaRepository bankAccountJpaRepository;
  private final BankAccountTransactionJpaRepository bankAccountTransactionJpaRepository;
  private final BankAccountMapper bankAccountMapper;
  private final BankAccountTransactionMapper bankAccountTransactionMapper;

  @Override
  public BankAccount save(BankAccount bankAccount) {
    log.debug("Saving bank account -> {}", bankAccount);
    final var bankAccountEntity = bankAccountMapper.toBankAccountEntity(bankAccount);
    bankAccountJpaRepository.save(bankAccountEntity);
    return bankAccount;
  }

  @Override
  public Optional<BankAccount> findById(BankAccountId bankAccountId) {
    log.debug("Retrieving bank account by id -> {}", bankAccountId.id());
    final var bankAccount = bankAccountJpaRepository.findByAccountCode(bankAccountId.id());
    final var bankAccountTransactions = bankAccountTransactionJpaRepository
        .findByDiscriminatorAccountCode(bankAccountId.id())
        .stream().map(bankAccountTransactionMapper::toBankAccountTransaction)
        .toList();

    return bankAccount
        .map(bankAccountEntity -> this.bankAccountMapper.toBankAccount(bankAccountEntity,
            bankAccountTransactions));
  }

  @Override
  public BankAccount update(BankAccount bankAccount) {
    log.debug("Updating bank account -> {}", bankAccount);
    final var accountEntity =
        this.bankAccountJpaRepository.findByAccountCode(bankAccount.id().id())
            .orElseThrow(() -> new EmptyResultDataAccessException("Invalid account code", 1));
    accountEntity.setAlias(bankAccount.aliasAccount());
    accountEntity.setConsolidatedBalance(bankAccount.consolidatedBalance().amount().floatValue());
    accountEntity.setIntervalBalance(bankAccount.intervalBalance().amount().floatValue());
    this.bankAccountJpaRepository.save(accountEntity);

    log.debug("Saving active transactions -> {}", bankAccount);
    bankAccount.activeTransactions().stream()
        .filter(BankAccountTransaction::newTransaction)
        .map(bankTx -> this.bankAccountTransactionMapper
            .toBankAccountTransactionEntity(bankTx, bankAccount.id().id()))
        .forEach(this.bankAccountTransactionJpaRepository::save);

    return bankAccount;
  }
}
