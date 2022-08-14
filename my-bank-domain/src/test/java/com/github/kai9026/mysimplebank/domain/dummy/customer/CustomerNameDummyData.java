package com.github.kai9026.mysimplebank.domain.dummy.customer;

import com.github.kai9026.mysimplebank.domain.customer.CustomerName;

public class CustomerNameDummyData {

  public static CustomerName createCustomerName(DummyData dummyData) {
    return CustomerName.of(dummyData.firstName, dummyData.lastName);
  }

  public enum DummyData {
    VALID_DATA("john", "wilkinson"),
    OTHER_VALID_DATA("kate", "moss"),
    INVALID_DATA_INVALID_FIRSTNAME(null, "moss"),
    INVALID_DATA_INVALID_LASTNAME("john", null);

    private final String firstName;
    private final String lastName;

    DummyData(final String firstName, final String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;

    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }
  }
}
