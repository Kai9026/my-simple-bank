package com.github.kai9026.mysimplebank.domain.bankaccount;

import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.MoneyDummyData.DummyData.OTHER_VALID_DATA_EUR;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.MoneyDummyData.DummyData.VALID_DATA_EUR;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.MoneyDummyData.DummyData.VALID_DATA_NEGATIVE_EUR;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.MoneyDummyData.DummyData.VALID_DATA_USD;
import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.MoneyDummyData.createMoney;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.kai9026.mysimplebank.domain.dummy.bankaccount.MoneyDummyData.DummyData;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  @DisplayName("Test creation with invalid currency, expected DomainValidationException")
  void createMoney_withInvalidCurrency_throwsException() {

    assertThatThrownBy(() -> createMoney(DummyData.INVALID_DATA_INCORRECT_CURRENCY))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Currency is not valid");
  }

  @Test
  @DisplayName("Test creation, expected to return money object")
  void createMoney_withValidData_shouldReturnMoney() {

    final var money = createMoney(VALID_DATA_EUR);

    assertNotNull(money);
    assertEquals(BigDecimal.valueOf(VALID_DATA_EUR.getAmount()).setScale(2, RoundingMode.HALF_EVEN),
        money.amount());
    assertEquals(VALID_DATA_EUR.getCurrency(), money.currency().getCurrencyCode());
  }

  @Test
  @DisplayName("Test sum two amounts, expected to return money object")
  void sumMoney_withValidData_shouldReturnMoney() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);
    final var otherMoneyEUR = createMoney(OTHER_VALID_DATA_EUR);

    final var sum = moneyEUR.sum(otherMoneyEUR);

    assertNotNull(sum);
    assertEquals(VALID_DATA_EUR.getAmount() + OTHER_VALID_DATA_EUR.getAmount(),
        sum.amount().doubleValue());
  }

  @Test
  @DisplayName("Test sum two amounts with different currency, throws DomainValidationException")
  void sumMoney_withDifferentCurrencies_throwsException() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);
    final var otherMoneyUSD = createMoney(VALID_DATA_USD);

    assertThatThrownBy(() -> moneyEUR.sum(otherMoneyUSD))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("You cannot sum amounts of different currencies");
  }

  @Test
  @DisplayName("Test subtract two amounts, expected to return money object")
  void subtractMoney_withValidData_shouldReturnMoney() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);
    final var otherMoneyEUR = createMoney(OTHER_VALID_DATA_EUR);

    final var sum = moneyEUR.subtract(otherMoneyEUR);

    assertNotNull(sum);
    assertEquals(VALID_DATA_EUR.getAmount() - OTHER_VALID_DATA_EUR.getAmount(),
        sum.amount().doubleValue());
  }

  @Test
  @DisplayName("Test subtract two amounts with different currency, throws DomainValidationException")
  void subtractMoney_withDifferentCurrencies_throwsException() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);
    final var otherMoneyUSD = createMoney(VALID_DATA_USD);

    assertThatThrownBy(() -> moneyEUR.subtract(otherMoneyUSD))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("You cannot subtract amounts of different currencies");
  }

  @Test
  @DisplayName("Test money equals or greater than zero, expected to return true")
  void comparingMoneyWithZero_withValueEqualOrGreaterThanZero_shouldReturnTrue() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);

    assertTrue(moneyEUR.isPositiveOrZero());
  }

  @Test
  @DisplayName("Test money equals or lower than zero, expected to return true")
  void comparingMoneyWithZero_withValueEqualOrLowerThanZero_shouldReturnFalse() {

    final var moneyEUR = createMoney(VALID_DATA_NEGATIVE_EUR);

    assertFalse(moneyEUR.isPositiveOrZero());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingMoney_withSameObjects_expectToBeEqual() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);
    final var sameMoneyEUR = createMoney(VALID_DATA_EUR);

    assertEquals(moneyEUR, sameMoneyEUR);
    assertEquals(moneyEUR.hashCode(), sameMoneyEUR.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingMoney_withDifferentObjects_expectNotToBeEqual() {

    final var moneyEUR = createMoney(VALID_DATA_EUR);
    final var anotherMoneyEUR = createMoney(OTHER_VALID_DATA_EUR);

    assertNotEquals(moneyEUR, anotherMoneyEUR);
    assertNotEquals(moneyEUR.hashCode(), anotherMoneyEUR.hashCode());
  }

}