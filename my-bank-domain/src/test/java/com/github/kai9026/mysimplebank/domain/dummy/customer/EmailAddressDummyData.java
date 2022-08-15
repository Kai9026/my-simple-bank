package com.github.kai9026.mysimplebank.domain.dummy.customer;

import com.github.kai9026.mysimplebank.domain.customer.EmailAddress;

public class EmailAddressDummyData {

  public static EmailAddress createEmail(DummyData dummyData) {
    return EmailAddress.of(dummyData.email);
  }

  public enum DummyData {
    VALID_DATA("pepe@hotmail.com"),
    OTHER_VALID_DATA("jose@gmail.com"),
    INVALID_DATA_INVALID_EMAIL("email@"),
    INVALID_DATA_NULL_EMAIL(null);

    private final String email;

    DummyData(final String email) {
      this.email = email;
    }

    public String getEmail() {
      return email;
    }

  }
}
