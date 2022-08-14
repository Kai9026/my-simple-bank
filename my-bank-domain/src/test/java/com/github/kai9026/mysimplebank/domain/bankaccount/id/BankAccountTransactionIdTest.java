package com.github.kai9026.mysimplebank.domain.bankaccount.id;

import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountTransactionIdDummyData.DummyData.INVALID_DATA_NULL_UUID;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountTransactionIdDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountTransactionIdDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountTransactionIdDummyData.createBankAccountTransactionId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountTransactionIdTest {

  @Test
  @DisplayName("Test creation with null BANK ACCOUNT TRANSACTION ID, expected IllegalArgumentException")
  void createBankAccountTransactionId_withNullId_throwsException() {

    assertThatThrownBy(() -> createBankAccountTransactionId(INVALID_DATA_NULL_UUID))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("BankAccountTransactionId cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected not null bankAccountId")
  void createBankAccountTransactionId_withValidParameters_returnObjectCreated() {

    final var bankAccountTransactionId = createBankAccountTransactionId(VALID_DATA);

    assertNotNull(bankAccountTransactionId);
    assertEquals(VALID_DATA.getId(), bankAccountTransactionId.id());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingBankAccountTransactionId_withSameObjects_expectToBeEqual() {

    final var bankAccountTransactionId = createBankAccountTransactionId(VALID_DATA);
    final var sameBankAccountTransactionId = createBankAccountTransactionId(VALID_DATA);

    assertEquals(bankAccountTransactionId, sameBankAccountTransactionId);
    assertEquals(bankAccountTransactionId.hashCode(), sameBankAccountTransactionId.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingBankAccountId_withDifferentObjects_expectNotToBeEqual() {

    final var bankAccountTransactionId = createBankAccountTransactionId(VALID_DATA);
    final var anotherBankAccountTransactionId = createBankAccountTransactionId(OTHER_VALID_DATA);

    assertNotEquals(bankAccountTransactionId, anotherBankAccountTransactionId);
    assertNotEquals(bankAccountTransactionId.hashCode(),
        anotherBankAccountTransactionId.hashCode());
  }

}