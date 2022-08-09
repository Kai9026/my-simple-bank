package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;

import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;

public class CustomerName implements ValueObject {

  private static final long serialVersionUID = 7234464042427484411L;
  private final String firstName;
  private final String lastName;

  private CustomerName(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static CustomerName of(final String firstName, final String lastName) {
    CustomerName.validateName(firstName, lastName);

    return new CustomerName(firstName, lastName);
  }

  private static void validateName(String firstName, String lastName) {
    if (isEmptyField(firstName) || isEmptyField(lastName)) {
      throw new IllegalArgumentException("Firstname and lastname cannot be null");
    }
  }

  public String firstName() {
    return this.firstName;
  }

  public String lastName() {
    return this.lastName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.firstName, this.lastName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherCustomerName = (CustomerName) o;
    return Objects.equals(this.firstName, otherCustomerName.firstName) &&
        Objects.equals(this.lastName, otherCustomerName.lastName);
  }

  @Override
  public String toString() {
    return "Customer name='" + this.firstName + " " + this.lastName + "'";
  }
}
