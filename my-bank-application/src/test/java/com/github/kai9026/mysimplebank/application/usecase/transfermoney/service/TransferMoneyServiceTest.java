package com.github.kai9026.mysimplebank.application.usecase.transfermoney.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.Money;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransferMoneyServiceTest {

  @Mock
  private BankAccountRepository bankAccountRepository;

  @InjectMocks
  private TransferMoneyService transferMoneyService;

  @Test
  @DisplayName("Test transfer money use case with invalid source account, throws InvalidInputDataException")
  void transferMoney_withInvalidSourceAccount_throwsException() {
    final var sourceAccountId = UUID.randomUUID();
    final var targetAccountId = UUID.randomUUID();

    when(this.bankAccountRepository.findById(BankAccountId.fromId(sourceAccountId)))
        .thenReturn(Optional.empty());

    final var transferDummyRequest =
        createTransferMoneyDummy(sourceAccountId, targetAccountId);
    assertThatThrownBy(() -> this.transferMoneyService.transferMoney(transferDummyRequest))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Source account is invalid");

    verify(this.bankAccountRepository, times(1))
        .findById(BankAccountId.fromId(sourceAccountId));
    verifyNoMoreInteractions(this.bankAccountRepository);
  }

  @Test
  @DisplayName("Test transfer money use case with invalid target account, throws InvalidInputDataException")
  void transferMoney_withInvalidTargetAccount_throwsException() {
    final var sourceAccountId = UUID.randomUUID();
    final var targetAccountId = UUID.randomUUID();

    when(this.bankAccountRepository.findById(BankAccountId.fromId(sourceAccountId)))
        .thenReturn(Optional.of(mock(BankAccount.class)));
    when(this.bankAccountRepository.findById(BankAccountId.fromId(targetAccountId)))
        .thenReturn(Optional.empty());

    final var transferDummyRequest =
        createTransferMoneyDummy(sourceAccountId, targetAccountId);
    assertThatThrownBy(() -> this.transferMoneyService.transferMoney(transferDummyRequest))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Target account is invalid");

    verify(this.bankAccountRepository, times(1))
        .findById(BankAccountId.fromId(sourceAccountId));
    verify(this.bankAccountRepository, times(1))
        .findById(BankAccountId.fromId(targetAccountId));
    verifyNoMoreInteractions(this.bankAccountRepository);
  }

  @Test
  @DisplayName("Test transfer money use case with invalid currency, throws InvalidInputDataException")
  void transferMoney_withInvalidCurrency_throwsException() {
    final var sourceAccountId = UUID.randomUUID();
    final var targetAccountId = UUID.randomUUID();

    when(this.bankAccountRepository.findById(BankAccountId.fromId(sourceAccountId)))
        .thenReturn(Optional.of(mock(BankAccount.class)));
    final var targetAccountMock = mock(BankAccount.class);
    when(targetAccountMock.defaultCurrency()).thenReturn("USD");
    when(this.bankAccountRepository.findById(BankAccountId.fromId(targetAccountId)))
        .thenReturn(Optional.of(targetAccountMock));

    final var transferDummyRequest =
        createTransferMoneyDummy(sourceAccountId, targetAccountId);
    assertThatThrownBy(() -> this.transferMoneyService.transferMoney(transferDummyRequest))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining(
            "Impossible to transfer with this currency. Incompatible Target account");

    verify(this.bankAccountRepository, times(2))
        .findById(any(BankAccountId.class));
    verifyNoMoreInteractions(this.bankAccountRepository);
  }

  @Test
  @DisplayName("Test transfer money use case with no enough money in source account, throws Exception")
  void transferMoney_withNoEnoughMoneyInSourceAccount_shouldUpdateBothAccounts() {
    final var sourceAccountId = UUID.randomUUID();
    final var targetAccountId = UUID.randomUUID();
    final var sourceAccount = this.createBankAccountEmpty(targetAccountId);
    final var targetAccount = this.createBankAccountWithEnoughMoney(sourceAccountId);
    when(this.bankAccountRepository.findById(BankAccountId.fromId(sourceAccountId)))
        .thenReturn(Optional.of(sourceAccount));
    when(this.bankAccountRepository.findById(BankAccountId.fromId(targetAccountId)))
        .thenReturn(Optional.of(targetAccount));

    final var transferDummyRequest =
        createTransferMoneyDummy(sourceAccountId, targetAccountId);

    assertThatThrownBy(() -> this.transferMoneyService.transferMoney(transferDummyRequest))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Impossible to perform the operation. Not enough money");

    verify(this.bankAccountRepository, times(2))
        .findById(any(BankAccountId.class));
    verifyNoMoreInteractions(this.bankAccountRepository);
  }


  @Test
  @DisplayName("Test transfer money use case with valid params, should update both bank accounts")
  void transferMoney_withValidParams_shouldUpdateBothAccounts() {
    final var sourceAccountId = UUID.randomUUID();
    final var targetAccountId = UUID.randomUUID();
    final var targetAccount = this.createBankAccountEmpty(targetAccountId);
    final var sourceAccount = this.createBankAccountWithEnoughMoney(sourceAccountId);
    when(this.bankAccountRepository.findById(BankAccountId.fromId(sourceAccountId)))
        .thenReturn(Optional.of(sourceAccount));
    when(this.bankAccountRepository.findById(BankAccountId.fromId(targetAccountId)))
        .thenReturn(Optional.of(targetAccount));

    final var transferDummyRequest =
        createTransferMoneyDummy(sourceAccountId, targetAccountId);
    this.transferMoneyService.transferMoney(transferDummyRequest);

    assertEquals(Money.of(6.00, "EUR"), sourceAccount.accountBalance());
    assertEquals(Money.of(4.00, "EUR"), targetAccount.accountBalance());
    verify(this.bankAccountRepository, times(2))
        .findById(any(BankAccountId.class));
    verify(this.bankAccountRepository, times(2))
        .update(any(BankAccount.class));
  }

  private TransferMoneyRequest createTransferMoneyDummy(UUID sourceAccountId,
      UUID targetAccountId) {
    return new TransferMoneyRequest(sourceAccountId, targetAccountId, 4.00,
        "EUR", "Transfer");
  }

  private BankAccount createBankAccountWithEnoughMoney(UUID accountId) {
    return BankAccount.initAccountWithTransactions("alias", "number", "EUR",
        accountId, UUID.randomUUID(), LocalDate.now(), 10.00, List.of());
  }

  private BankAccount createBankAccountEmpty(UUID accountId) {
    return BankAccount.initAccountWith("alias", "EUR", accountId, UUID.randomUUID(),
        LocalDate.now());
  }

}