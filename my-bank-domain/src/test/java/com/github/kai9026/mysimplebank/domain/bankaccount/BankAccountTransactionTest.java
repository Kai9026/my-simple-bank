package com.github.kai9026.mysimplebank.domain.bankaccount;

import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountTransactionDummyData.DummyData.INVALID_DATA_NULL_AMOUNT;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountTransactionDummyData.DummyData.INVALID_DATA_NULL_CONCEPT;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountTransactionDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountTransactionDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountTransactionDummyData.createBankAccountTransaction;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountTransactionTest {

  @Test
  @DisplayName("Test creation with null amount, expected DomainValidationException")
  void createBankAccountTransaction_withNullAmount_throwsException() {

    assertThatThrownBy(() -> createBankAccountTransaction(INVALID_DATA_NULL_AMOUNT))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Transaction amount cannot be null");
  }

  @Test
  @DisplayName("Test creation with null concept, expected DomainValidationException")
  void createBankAccountTransaction_withNullConcept_throwsException() {

    assertThatThrownBy(() -> createBankAccountTransaction(INVALID_DATA_NULL_CONCEPT))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Transaction concept cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected bank account transaction aggregate")
  void createBankAccountTransaction_withValidParameters_returnObjectCreated() {

    final var bankAccountTransaction = createBankAccountTransaction(VALID_DATA);

    assertNotNull(bankAccountTransaction);
    assertEquals(VALID_DATA.getTransactionAccountId(), bankAccountTransaction.id().id());
    assertEquals(VALID_DATA.getOriginBankAccountId(), bankAccountTransaction.originAccountCode());
    assertEquals(VALID_DATA.getTargetBankAccountId(), bankAccountTransaction.targetAccountCode());
    assertEquals(VALID_DATA.getTransactionAmount(), bankAccountTransaction.transactionAmount());
    assertEquals(VALID_DATA.getTransactionDate(), bankAccountTransaction.transactionDate());
    assertEquals(VALID_DATA.getTransactionConcept(),
        bankAccountTransaction.concept());
  }

  @Test
  @DisplayName("Test with same object, expected be equals")
  void comparingBankAccountTransaction_withSameObject_expectToBeEqual() {

    final var bankAccountTransaction = createBankAccountTransaction(VALID_DATA);
    final var sameBankAccountTransaction = createBankAccountTransaction(VALID_DATA);

    assertEquals(bankAccountTransaction, sameBankAccountTransaction);
    assertEquals(bankAccountTransaction.hashCode(), sameBankAccountTransaction.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different")
  void comparingBankAccountTransaction_withDifferentObject_expectNotToBeEqual() {

    final var bankAccountTransaction = createBankAccountTransaction(VALID_DATA);
    final var anotherBankAccountTransaction = createBankAccountTransaction(OTHER_VALID_DATA);

    assertNotEquals(bankAccountTransaction, anotherBankAccountTransaction);
    assertNotEquals(bankAccountTransaction.hashCode(), anotherBankAccountTransaction.hashCode());
  }
}