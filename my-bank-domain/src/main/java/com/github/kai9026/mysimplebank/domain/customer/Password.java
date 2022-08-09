package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;

import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;

public class Password implements ValueObject {

  private static final long serialVersionUID = 298909603642127271L;
  private static final String BLUR_STRING = "XXXXXX";

  private final String pwd;

  private Password(final String password) {
    this.pwd = password;
  }

  public static Password of(final String password) {
    Password.validatePassword(password);

    return new Password(password);
  }

  private static void validatePassword(String password) {
    if (isEmptyField(password)) {
      throw new IllegalArgumentException("Password cannot be null");
    }
  }

  public String pwd() {
    return this.pwd;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pwd);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherPassword = (Password) o;
    return Objects.equals(this.pwd, otherPassword.pwd);
  }

  @Override
  public String toString() {
    final var plainPart = this.pwd.substring(this.pwd.length() - 2);
    return "Password='" + BLUR_STRING + plainPart + "'";
  }
}
