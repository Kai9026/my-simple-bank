package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountDetailRequestTest {

  @Test
  @DisplayName("Test creation of bank account detail request with invalid account, throws Exception")
  void createBankAccountDetailRequest_withInvalidAccount_throwsException() {

    assertThatThrownBy(() ->
        new BankAccountDetailRequest(null, LocalDate.now(),
            LocalDate.now().plusMonths(1)))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("Account code is required");
  }

  @Test
  @DisplayName("Test creation of bank account detail request with invalid dates, throws Exception")
  void createBankAccountDetailRequest_withInvalidDates_throwsException() {

    assertThatThrownBy(() ->
        new BankAccountDetailRequest(UUID.randomUUID(), LocalDate.now(),
            LocalDate.now().minusMonths(1)))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining("'To' param cannot be earlier than 'From' param");
  }

  @Test
  @DisplayName("Test creation of bank account detail request with valid params, should return created request")
  void createBankAccountDetailRequest_withValidParams_shouldReturnRequest() {

    final var bankAccountDetailRequest = new BankAccountDetailRequest(UUID.randomUUID(),
        LocalDate.now(),
        LocalDate.now().plusMonths(1));
    assertNotNull(bankAccountDetailRequest);
  }
}