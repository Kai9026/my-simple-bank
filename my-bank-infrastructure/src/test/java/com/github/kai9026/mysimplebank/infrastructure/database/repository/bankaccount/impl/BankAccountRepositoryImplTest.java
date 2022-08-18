package com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountEntity;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountTransactionEntity;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountTransactionMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.BankAccountJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.BankAccountTransactionJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
class BankAccountRepositoryImplTest {

  @Mock
  private BankAccountJpaRepository bankAccountJpaRepository;

  @Mock
  private BankAccountTransactionJpaRepository bankAccountTransactionJpaRepository;

  @Mock
  private BankAccountMapper bankAccountMapper;

  @Mock
  private BankAccountTransactionMapper bankAccountTransactionMapper;

  @InjectMocks
  private BankAccountRepositoryImpl bankAccountRepository;

  @Test
  @DisplayName("Test save bank account, bank account is saved")
  void saveBankAccount_shouldReturnSavedBankAccountAggregate() {
    when(this.bankAccountMapper.toBankAccountEntity(any(BankAccount.class)))
        .thenReturn(mock(BankAccountEntity.class));
    when(this.bankAccountJpaRepository.save(any(BankAccountEntity.class)))
        .thenReturn(mock(BankAccountEntity.class));

    final var bankAccount =
        this.bankAccountRepository.save(BankAccountDummyData.createBankAccount());

    assertNotNull(bankAccount);
    verify(this.bankAccountJpaRepository, times(1))
        .save(any(BankAccountEntity.class));
    verify(this.bankAccountMapper, times(1))
        .toBankAccountEntity(any(BankAccount.class));
  }

  @Test
  @DisplayName("Test get bank account by id, bank account and transactions are retrieved")
  void getBankAccountById_shouldReturnBankAccountAggregate() {
    when(this.bankAccountJpaRepository.findByAccountCode(any(UUID.class)))
        .thenReturn(Optional.of(mock(BankAccountEntity.class)));
    when(this.bankAccountTransactionJpaRepository.findByOriginAccountCodeOrTargetAccountCode(
        any(UUID.class), any(UUID.class)))
        .thenReturn(List.of(mock(BankAccountTransactionEntity.class)));
    when(this.bankAccountTransactionMapper.toBankAccountTransaction(
        any(BankAccountTransactionEntity.class)))
        .thenReturn(mock(BankAccountTransaction.class));
    when(this.bankAccountMapper.toBankAccount(any(BankAccountEntity.class), any(List.class)))
        .thenReturn(mock(BankAccount.class));

    final var bankAccount =
        this.bankAccountRepository.findById(BankAccountId.fromId(UUID.randomUUID()));

    assertNotNull(bankAccount);
    verify(this.bankAccountJpaRepository, times(1))
        .findByAccountCode(any(UUID.class));
    verify(this.bankAccountTransactionJpaRepository, times(1))
        .findByOriginAccountCodeOrTargetAccountCode(any(UUID.class), any(UUID.class));
    verify(this.bankAccountTransactionMapper, times(1))
        .toBankAccountTransaction(any(BankAccountTransactionEntity.class));
    verify(this.bankAccountMapper, times(1))
        .toBankAccount(any(BankAccountEntity.class), any(List.class));
  }

  @Test
  @DisplayName("Test update bank account, bank account and transactions are updated")
  void updateBankAccount_shouldReturnBankAccountAggregate() {
    when(this.bankAccountJpaRepository.findByAccountCode(any(UUID.class)))
        .thenReturn(Optional.of(mock(BankAccountEntity.class)));
    when(this.bankAccountJpaRepository.save(any(BankAccountEntity.class)))
        .thenReturn(mock(BankAccountEntity.class));
    when(this.bankAccountTransactionMapper.toBankAccountTransactionEntity(
        any(BankAccountTransaction.class)))
        .thenReturn(mock(BankAccountTransactionEntity.class));
    when(this.bankAccountTransactionJpaRepository.save(any(BankAccountTransactionEntity.class)))
        .thenReturn(mock(BankAccountTransactionEntity.class));

    final var bankAccount =
        this.bankAccountRepository.update(BankAccountDummyData.createBankAccount());

    assertNotNull(bankAccount);
    verify(this.bankAccountJpaRepository, times(1))
        .findByAccountCode(any(UUID.class));
    verify(this.bankAccountJpaRepository, times(1))
        .save(any(BankAccountEntity.class));
    verify(this.bankAccountTransactionMapper, times(1))
        .toBankAccountTransactionEntity(any(BankAccountTransaction.class));
    verify(this.bankAccountTransactionJpaRepository, times(1))
        .save(any(BankAccountTransactionEntity.class));
  }

  @Test
  @DisplayName("Test update bank account, if bank account is not valid then throws exception")
  void updateBankAccount_WithInvalidAccountCode_throwsException() {
    when(this.bankAccountJpaRepository.findByAccountCode(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
        this.bankAccountRepository.update(BankAccountDummyData.createBankAccount()))
        .isInstanceOf(EmptyResultDataAccessException.class)
        .hasMessageContaining("Invalid account code");
  }
}