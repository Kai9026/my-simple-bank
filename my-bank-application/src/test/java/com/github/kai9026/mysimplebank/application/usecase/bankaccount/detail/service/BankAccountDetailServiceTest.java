package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.mapper.BankAccountDetailMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
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
class BankAccountDetailServiceTest {

  private static final LocalDate INIT_INTERVAL = LocalDate.now().minusMonths(1);
  private static final LocalDate END_INTERVAL = INIT_INTERVAL.plusMonths(6);

  @Mock
  private BankAccountRepository bankAccountRepository;

  @Mock
  private BankAccountDetailMapper bankAccountDetailMapper;

  @InjectMocks
  private BankAccountDetailService bankAccountDetailService;

  @Test
  @DisplayName("Test retrieve bank account detail with invalid data, throws InvalidInputDataException")
  void retrieveBankAccountDetail_withInvalidData_throwsException() {

    doThrow(new InvalidInputDataException("Invalid account"))
        .when(this.bankAccountRepository).findById(any(BankAccountId.class));

    final var request =
        new BankAccountDetailRequest(UUID.randomUUID(), INIT_INTERVAL, END_INTERVAL);
    assertThatThrownBy(() -> this.bankAccountDetailService.getBankAccountDetail(request))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Invalid account");

  }

  @Test
  @DisplayName("Test retrieve bank account detail without errors, should return account detail response")
  void retrieveBankAccountDetail_shouldReturnAccountDetail() {
    final var bankAccountId = BankAccountId.fromId(UUID.randomUUID());
    final var bankAccountDummy = this.createBankAccountDummy(bankAccountId);
    when(bankAccountRepository.findById(any(BankAccountId.class)))
        .thenReturn(Optional.of(bankAccountDummy));
    when(bankAccountDetailMapper.toBankAccountDetailResponse(anyFloat(), any(List.class)))
        .thenReturn(createBankAccountDetailDummy());

    final var startDate = LocalDate.now().minusWeeks(1);
    final var endDate = startDate.plusWeeks(3);
    final var request =
        new BankAccountDetailRequest(bankAccountId.id(),
            startDate, endDate);
    final var bankAccountDetail =
        this.bankAccountDetailService.getBankAccountDetail(request);

    assertNotNull(bankAccountDetail);
    verify(this.bankAccountRepository, times(1))
        .findById(any(BankAccountId.class));
    verify(this.bankAccountDetailMapper, times(1))
        .toBankAccountDetailResponse(anyFloat(), any(List.class));
  }

  private BankAccountDetailResponse createBankAccountDetailDummy() {
    return new BankAccountDetailResponse(10.00F, List.of());
  }

  private BankAccount createBankAccountDummy(BankAccountId accountId) {
    List<BankAccountTransaction> transactions = List.of(
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            accountId, BankAccountId.fromId(UUID.randomUUID()),
            Money.of(4.00, "EUR"), "Tx1", LocalDate.now(), false),
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(UUID.randomUUID()), accountId,
            Money.of(5.00, "EUR"), "Tx1", LocalDate.now(), false)
    );

    return BankAccount.initAccountWithTransactions("alias", "3214 4245 8473 3213",
        "EUR", accountId.id(), UUID.randomUUID(), INIT_INTERVAL, 10.00,
        transactions);
  }

}