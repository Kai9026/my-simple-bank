package com.github.kai9026.mysimplebank.domain.bankaccount;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountNumberTest {

  @Test
  @DisplayName("Test generation, expected not null bank account number")
  void generateBankAccountNumber_returnObjectCreated() {

    final var expectedAccountNumber = BankAccountNumber.generate();

    assertNotNull(expectedAccountNumber.number());
  }

  @Test
  @DisplayName("Comparing with other account number, expected to be different numbers")
  void comparingBankAccount_expectToBeNotEqual() {

    final var accountNumber = BankAccountNumber.generate();
    final var anotherAccountNumber = BankAccountNumber.generate();

    assertNotEquals(accountNumber, anotherAccountNumber);
    assertNotEquals(accountNumber.hashCode(), anotherAccountNumber.hashCode());
  }

}