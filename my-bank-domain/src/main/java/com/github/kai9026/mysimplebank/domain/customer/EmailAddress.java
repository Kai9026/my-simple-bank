package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;
import java.util.regex.Pattern;

public class EmailAddress implements ValueObject {

  private static final long serialVersionUID = -2055286373996616866L;
  private static final String REGEX_VALID_EMAIL =
      "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

  private final String email;

  private EmailAddress(final String email) {
    this.email = email;
  }

  public static EmailAddress of(final String email) {
    EmailAddress.validateEmail(email);

    return new EmailAddress(email);
  }

  private static void validateEmail(String email) {
    if (isEmptyField(email)) {
      throw new DomainValidationException("Email cannot be null");
    }

    final var regexPattern = Pattern.compile(REGEX_VALID_EMAIL);
    final var matcher = regexPattern.matcher(email);
    if (!matcher.matches()) {
      throw new DomainValidationException("Email format is not valid");
    }
  }

  public String email() {
    return this.email;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.email);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherEmail = (EmailAddress) o;
    return Objects.equals(this.email, otherEmail.email);
  }

  @Override
  public String toString() {
    return "Email address='" + this.email + "'";
  }
}
