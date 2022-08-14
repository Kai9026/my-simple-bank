package com.github.kai9026.mysimplebank.domain.bankaccount;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class Money implements ValueObject {

  private static final int SCALE = 2;
  private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

  private final BigDecimal amount;
  private final Currency currencyCode;

  private Money(BigDecimal amount, Currency currencyCode) {
    this.amount = amount;
    this.currencyCode = currencyCode;
  }

  public static Money of(final Double amount, final String currency) {
    try {

      final var defaultCurrency = Currency.getInstance(currency);
      final var amountBigDecimal = BigDecimal.valueOf(amount)
          .setScale(SCALE, ROUNDING_MODE);
      return new Money(amountBigDecimal, defaultCurrency);
    } catch (IllegalArgumentException ex) {
      throw new DomainValidationException("Currency is not valid");
    }
  }

  public BigDecimal amount() {
    return this.amount.setScale(SCALE, ROUNDING_MODE);
  }

  public Currency currency() {
    return this.currencyCode;
  }

  public Money sum(final Money money) {
    if (!money.currencyCode.equals(this.currencyCode)) {
      throw new DomainValidationException("You cannot sum amounts of different currencies");
    }
    final var otherAmount = money.amount();
    final var totalAmount = this.amount.add(otherAmount).setScale(2, ROUNDING_MODE);
    return new Money(totalAmount, currency());
  }

  public Money subtract(final Money money) {
    if (!money.currencyCode.equals(this.currencyCode)) {
      throw new DomainValidationException("You cannot subtract amounts of different currencies");
    }
    final var otherAmount = money.amount();
    final var totalAmount = this.amount.subtract(otherAmount).setScale(2, ROUNDING_MODE);
    return new Money(totalAmount, currency());
  }

  public boolean isPositiveOrZero() {
    return this.amount.compareTo(BigDecimal.ZERO) >= 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.amount, this.currencyCode);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherMoney = (Money) o;
    return Objects.equals(this.amount, otherMoney.amount) &&
        Objects.equals(this.currencyCode, otherMoney.currencyCode);
  }

  @Override
  public String toString() {
    return "Money{" +
        "amount=" + amount +
        ", defaultCurrency=" + currencyCode +
        '}';
  }
}
