package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.BankAccountDepositUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankAccountDepositServiceTest {

  private final BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
  private final BankAccountDepositUseCaseMapper bankAccountDepositUseCaseMapper =
      mock(BankAccountDepositUseCaseMapper.class);

  private BankAccountDepositService bankAccountDepositService;

  @BeforeEach
  private void init() {
    this.bankAccountDepositService = new BankAccountDepositService(bankAccountRepository,
        bankAccountDepositUseCaseMapper);
  }

  @Test
  @DisplayName("Test bank account deposit use case with invalid account, throws InvalidInputDataException")
  void depositIntoBankAccount_withInvalidAccount_throwsException() {
    when(this.bankAccountRepository.findById(any(BankAccountId.class)))
        .thenReturn(Optional.empty());

    final var request =
        new BankAccountDepositRequest(10.00, "EUR", UUID.randomUUID(), "Savings");

    assertThatThrownBy(() -> this.bankAccountDepositService.depositIntoAccount(request))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Invalid bank account");
    verify(this.bankAccountRepository, times(1))
        .findById(any(BankAccountId.class));
    verifyNoMoreInteractions(this.bankAccountRepository);
    verifyNoInteractions(this.bankAccountDepositUseCaseMapper);
  }

  @Test
  @DisplayName("Test bank account deposit use case with invalid params, throws InvalidInputDataException")
  void depositIntoBankAccount_withInvalidParams_throwsException() {
    when(this.bankAccountRepository.findById(any(BankAccountId.class)))
        .thenReturn(dummyBankAccount());

    final var request =
        new BankAccountDepositRequest(10.00, "EUR", UUID.randomUUID(), "Savings");

    assertThatThrownBy(() -> this.bankAccountDepositService.depositIntoAccount(request))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Currency is incorrect. Must be non empty and 'USD'");
    verify(this.bankAccountRepository, times(1))
        .findById(any(BankAccountId.class));
    verifyNoMoreInteractions(this.bankAccountRepository);
    verifyNoInteractions(this.bankAccountDepositUseCaseMapper);
  }

  @Test
  @DisplayName("Test bank account deposit use case, expected to deposit into account")
  void depositIntoBankAccount_withValidParams_shouldDoDepositIntoAccount() {
    when(this.bankAccountRepository.findById(any(BankAccountId.class)))
        .thenReturn(Optional.of(mock(BankAccount.class)));
    when(this.bankAccountRepository.update(any(BankAccount.class)))
        .thenReturn(mock(BankAccount.class));
    when(this.bankAccountDepositUseCaseMapper.toBankAccountBaseResponse(any(BankAccount.class)))
        .thenReturn(dummyBankAccountBaseResponse());

    final var request =
        new BankAccountDepositRequest(10.00, "EUR", UUID.randomUUID(), "Savings");
    final var bankAccountDepositResponse =
        this.bankAccountDepositService.depositIntoAccount(request);

    assertNotNull(bankAccountDepositResponse);
    verify(this.bankAccountRepository, times(1))
        .findById(any(BankAccountId.class));
    verify(this.bankAccountRepository, times(1))
        .update(any(BankAccount.class));
    verify(this.bankAccountDepositUseCaseMapper, times(1))
        .toBankAccountBaseResponse(any(BankAccount.class));

  }

  private BankAccountBaseResponse dummyBankAccountBaseResponse() {
    return new BankAccountBaseResponse(
        UUID.randomUUID(), "number", 0.00, "alias",
        "EUR", UUID.randomUUID());
  }

  private Optional<BankAccount> dummyBankAccount() {
    return Optional.of(
        BankAccount.initAccountWith("alias", "USD", UUID.randomUUID(), UUID.randomUUID(),
            LocalDate.now()));
  }

}