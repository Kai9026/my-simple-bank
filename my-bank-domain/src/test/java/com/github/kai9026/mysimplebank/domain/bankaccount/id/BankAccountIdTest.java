package com.github.kai9026.mysimplebank.domain.bankaccount.id;

import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountIdDummyData.DummyData.INVALID_DATA_NULL_UUID;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountIdDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountIdDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id.BankAccountIdDummyData.createBankAccountId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountIdTest {

  @Test
  @DisplayName("Test creation with null BANK ACCOUNT ID, expected IllegalArgumentException")
  void createBankAccountId_withNullId_throwsException() {

    assertThatThrownBy(() -> createBankAccountId(INVALID_DATA_NULL_UUID))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("BankAccountId cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected not null bankAccountId")
  void createBankAccountId_withValidParameters_returnObjectCreated() {

    final var bankAccountId = createBankAccountId(VALID_DATA);

    assertNotNull(bankAccountId);
    assertEquals(VALID_DATA.getId(), bankAccountId.id());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingBankAccountId_withSameObjects_expectToBeEqual() {

    final var bankAccountId = createBankAccountId(VALID_DATA);
    final var sameBankAccountId = createBankAccountId(VALID_DATA);

    assertEquals(bankAccountId, sameBankAccountId);
    assertEquals(bankAccountId.hashCode(), sameBankAccountId.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingBankAccountId_withDifferentObjects_expectNotToBeEqual() {

    final var bankAccountId = createBankAccountId(VALID_DATA);
    final var anotherBankAccountId = createBankAccountId(OTHER_VALID_DATA);

    assertNotEquals(bankAccountId, anotherBankAccountId);
    assertNotEquals(bankAccountId.hashCode(), anotherBankAccountId.hashCode());
  }
}