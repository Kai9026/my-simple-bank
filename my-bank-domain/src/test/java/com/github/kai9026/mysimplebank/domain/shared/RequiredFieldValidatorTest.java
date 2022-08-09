package com.github.kai9026.mysimplebank.domain.shared;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequiredFieldValidatorTest {

  @Test
  @DisplayName("Test validate empty field, expected true")
  void validateEmptyField_withEmptyField_returnTrue() {
    final String emptyField = "";
    assertTrue(RequiredFieldValidator.isEmptyField(emptyField));
  }

  @Test
  @DisplayName("Test validate empty field, expected false")
  void validateEmptyField_withNoEmptyField_returnFalse() {
    final String emptyField = "notEmpty";
    assertFalse(RequiredFieldValidator.isEmptyField(emptyField));
  }
}