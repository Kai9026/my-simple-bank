package com.github.kai9026.mysimplebank.domain.dummy.bankaccount;

import com.github.kai9026.mysimplebank.domain.bankaccount.Money;

public class MoneyDummyData {

  public static Money createMoney(DummyData dummyData) {
    return Money.of(dummyData.amount, dummyData.currency);
  }

  public enum DummyData {
    VALID_DATA_EUR(15.00, "EUR"),
    OTHER_VALID_DATA_EUR(10.00, "EUR"),
    VALID_DATA_USD(4.00, "USD"),

    VALID_DATA_NEGATIVE_EUR(-5.00, "EUR"),
    INVALID_DATA_INCORRECT_CURRENCY(10.00, "EEE");

    private final Double amount;
    private final String currency;

    DummyData(final Double amount, final String currency) {
      this.amount = amount;
      this.currency = currency;
    }

    public Double getAmount() {
      return amount;
    }

    public String getCurrency() {
      return currency;
    }
  }

}
