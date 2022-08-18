package com.github.kai9026.mysimplebank.domain.bankaccount;

import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;
import java.util.Random;

public class BankAccountNumber implements ValueObject {

  private static final long serialVersionUID = 236591421574005370L;

  private final String number;

  private BankAccountNumber(String number) {
    this.number = number;
  }

  public static BankAccountNumber generate() {
    // It is not totally random, simplified solution for demo
    Random rnd = new Random();
    final var group1 = String.format("%04d", rnd.nextInt(9999));
    final var group2 = String.format("%04d", rnd.nextInt(9999));
    final var group3 = String.format("%04d", rnd.nextInt(9999));
    final var group4 = String.format("%04d", rnd.nextInt(9999));

    return new BankAccountNumber(group1 + " " + group2 + " " + group3 + " " + group4);
  }

  public static BankAccountNumber fromString(final String accountNumber) {
    return new BankAccountNumber(accountNumber);
  }

  public String number() {
    return this.number;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.number);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherNumber = (BankAccountNumber) o;
    return Objects.equals(this.number, otherNumber.number);
  }

  @Override
  public String toString() {
    return "Bank account number='" + this.number + "'";
  }
}
