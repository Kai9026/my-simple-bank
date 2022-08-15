package com.github.kai9026.mysimplebank.domain.bankaccount;

import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountDummyData.*;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountDummyData.DummyData.INVALID_DATA_NULL_CURRENCY;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountDummyData.DummyData.INVALID_DATA_NULL_CUSTOMER;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountDummyData.DummyData.VALID_DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountTest {

  @Test
  @DisplayName("Test initialize with null currency, expected DomainValidationException")
  void createBankAccount_withNullCurrency_throwsException() {
    assertThatThrownBy(() -> createBankAccount(INVALID_DATA_NULL_CURRENCY))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Currency cannot be null");

  }

  @Test
  @DisplayName("Test initialize with null customer, expected DomainValidationException")
  void createBankAccount_withNullCustomer_throwsException() {
    assertThatThrownBy(() -> createBankAccount(INVALID_DATA_NULL_CUSTOMER))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Customer code is required");

  }

  @Test
  @DisplayName("Test initialize with valid parameters, expected to return BankAccount aggregate")
  void createBankAccount_withValidParameters_throwsException() {
    final var bankAccount = createBankAccount(VALID_DATA);

    assertNotNull(bankAccount);
    assertEquals(Money.of(0.00, "EUR"), bankAccount.accountBalance());
    assertEquals(LocalDate.now(), bankAccount.openIntervalDate());
    assertEquals(LocalDate.now().plusMonths(6), bankAccount.closeIntervalDate());
    assertEquals(VALID_DATA.getCustomerCode(), bankAccount.accountCustomer().id());
    assertEquals(VALID_DATA.getDefaultCurrency(), bankAccount.defaultCurrency());
    assertEquals(VALID_DATA.getAliasAccount(), bankAccount.aliasAccount());
    assertThat(bankAccount.activeTransactions()).isEmpty();
  }

  @Test
  @DisplayName("Test initialize with transactions, expected to return BankAccount aggregate")
  void createBankAccount_withValidTransactions_throwsException() {
    final var bankAccount = createBankAccountWithValidTransactions();

    assertNotNull(bankAccount);
    assertEquals(Money.of(105.00, "EUR"), bankAccount.accountBalance());
    assertEquals(LocalDate.now().minusDays(1), bankAccount.openIntervalDate());
    assertEquals(LocalDate.now().minusDays(1).plusMonths(6),
        bankAccount.closeIntervalDate());
    assertThat(bankAccount.activeTransactions()).hasSize(3);
  }

  @Test
  @DisplayName("Test updating alias account, expected to update alias")
  void updateAlias_shouldChangeAlias() {
    final var newAlias = "Saving Black Friday account";
    final var bankAccount = createBankAccount(VALID_DATA);

    assertEquals(VALID_DATA.getAliasAccount(), bankAccount.aliasAccount());

    bankAccount.changeAliasAccount(newAlias);

    assertEquals(newAlias, bankAccount.aliasAccount());
  }

  @Test
  @DisplayName("Test deposit money into account, expected to add transaction and increment balance")
  void depositMoney_withValidParams_shouldUpdateBankAccount() {
    final var bankAccount = createBankAccount(VALID_DATA);

    bankAccount.depositMoney(25.00, "EUR", "Bizum DAZN",
        BankAccountId.fromId(UUID.randomUUID()));

    assertEquals(Money.of(25.00, "EUR"), bankAccount.accountBalance());
    assertThat(bankAccount.activeTransactions()).hasSize(1);
  }

  @Test
  @DisplayName("Test deposit money into account, throws DomainValidationException")
  void depositMoney_withInvalidParams_throwsException() {
    final var bankAccount = createBankAccount(VALID_DATA);

    assertThatThrownBy(() -> bankAccount.depositMoney(25.00, "USD", "Bizum DAZN",
        BankAccountId.fromId(UUID.randomUUID())))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Currency is incorrect. Must be non empty and 'EUR'");
  }

  @Test
  @DisplayName("Test with same object, expected be equals")
  void comparingBankAccount_withSameObject_expectToBeEqual() {

    final var bankAccount = createBankAccount(VALID_DATA);
    final var sameBankAccount = createBankAccount(VALID_DATA);

    assertEquals(bankAccount, sameBankAccount);
    assertEquals(bankAccount.hashCode(), sameBankAccount.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different")
  void comparingBankAccount_withDifferentObject_expectNotToBeEqual() {

    final var bankAccount = createBankAccount(VALID_DATA);
    final var anotherBankAccount = createBankAccount(OTHER_VALID_DATA);

    assertNotEquals(bankAccount, anotherBankAccount);
    assertNotEquals(bankAccount.hashCode(), anotherBankAccount.hashCode());
  }

}