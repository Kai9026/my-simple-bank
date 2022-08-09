package com.github.kai9026.mysimplebank.domain.dummy;

import com.github.kai9026.mysimplebank.domain.customer.Password;

public class PasswordDummyData {

  public static Password createPassword(DummyData dummyData) {
    return Password.of(dummyData.password);
  }

  public enum DummyData {
    VALID_DATA("xjcerr899."),
    OTHER_VALID_DATA("psdfdf$23"),
    INVALID_DATA_NULL_PASSWORD(null);

    private final String password;

    DummyData(final String password) {
      this.password = password;
    }

    public String getPassword() {
      return password;
    }

  }
}
