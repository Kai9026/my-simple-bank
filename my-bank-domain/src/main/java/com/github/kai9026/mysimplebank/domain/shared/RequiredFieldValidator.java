package com.github.kai9026.mysimplebank.domain.shared;

import java.util.Objects;

public final class RequiredFieldValidator {

  private RequiredFieldValidator() {
  }

  public static boolean isEmptyField(final String field) {
    return Objects.isNull(field) || field.isBlank();
  }

}