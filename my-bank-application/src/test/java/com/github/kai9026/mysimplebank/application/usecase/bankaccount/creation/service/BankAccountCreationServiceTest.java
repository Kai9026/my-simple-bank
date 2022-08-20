package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper.BankAccountCreationUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankAccountCreationServiceTest {

  @Mock
  private BankAccountRepository bankAccountRepository;
  @Mock
  private BankAccountCreationUseCaseMapper bankAccountCreationUseCaseMapper;

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private BankAccountCreationService bankAccountCreationService;

  @Test
  @DisplayName("Test bank account creation use case with invalid data, throws InvalidInputDataException")
  void createBankAccount_withInvalidData_throwsException() {
    when(this.customerRepository.findById(any(CustomerId.class)))
        .thenReturn(Optional.of(mock(Customer.class)));
    doThrow(new InvalidInputDataException("Account alias is required")).when(
            this.bankAccountCreationUseCaseMapper)
        .toBankAccount(any(BankAccountCreationRequest.class));

    final var request =
        new BankAccountCreationRequest("alias", "EUR", UUID.randomUUID());
    assertThatThrownBy(() -> this.bankAccountCreationService.createNewBankAccount(request))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Account alias is required");
  }

  @Test
  @DisplayName("Test bank account creation use case with invalid customer, throws InvalidInputDataException")
  void createBankAccount_withInvalidCustomer_throwsException() {
    doThrow(new InvalidInputDataException("Invalid customer")).when(
            this.customerRepository)
        .findById(any(CustomerId.class));

    final var request =
        new BankAccountCreationRequest("alias", "EUR", UUID.randomUUID());
    assertThatThrownBy(() -> this.bankAccountCreationService.createNewBankAccount(request))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Invalid customer");
  }

  @Test
  @DisplayName("Test bank account creation use case, expect return created account")
  void createBankAccount_withValidParameters_shouldCreateCustomer() {
    when(this.customerRepository.findById(any(CustomerId.class)))
        .thenReturn(Optional.of(mock(Customer.class)));
    when(this.bankAccountCreationUseCaseMapper.toBankAccount(any(BankAccountCreationRequest.class)))
        .thenReturn(mock(BankAccount.class));
    when(this.bankAccountRepository.save(any(BankAccount.class)))
        .thenReturn(dummyBankAccount());
    when(this.bankAccountCreationUseCaseMapper.toBankAccountBaseResponse(any(BankAccount.class)))
        .thenReturn(bankAccountBaseResponseDummy());

    final var request =
        new BankAccountCreationRequest("alias", "EUR", UUID.randomUUID());
    final var newBankAccount = this.bankAccountCreationService.createNewBankAccount(request);

    assertNotNull(newBankAccount);
    verify(this.bankAccountRepository, times(1))
        .save(any(BankAccount.class));
    verify(this.bankAccountCreationUseCaseMapper, times(1))
        .toBankAccount(any(BankAccountCreationRequest.class));
    verify(this.bankAccountCreationUseCaseMapper, times(1))
        .toBankAccountBaseResponse(any(BankAccount.class));
  }

  private BankAccountBaseResponse bankAccountBaseResponseDummy() {
    return new BankAccountBaseResponse(UUID.randomUUID(),
        "number", 0.00, "alias",
        "EUR", UUID.randomUUID());
  }

  private BankAccount dummyBankAccount() {
    return BankAccount.initAccountWith("alias", "EUR", UUID.randomUUID(),
        UUID.randomUUID(), LocalDate.now());
  }
}