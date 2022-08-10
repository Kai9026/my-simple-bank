package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.dummy.PasswordDummyData.DummyData.INVALID_DATA_NULL_PASSWORD;
import static com.github.kai9026.mysimplebank.domain.dummy.PasswordDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.PasswordDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.PasswordDummyData.createPassword;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordTest {

  @Test
  @DisplayName("Test creation with null EMAIL_ADDRESS, expected DomainValidationException")
  void createPassword_withNullEmailAddress_throwsException() {

    assertThatThrownBy(() -> createPassword(INVALID_DATA_NULL_PASSWORD))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Password cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected not null password")
  void createPassword_withValidParameters_returnObjectCreated() {

    final var expectedPassword = createPassword(VALID_DATA);

    assertNotNull(expectedPassword);
    assertEquals(VALID_DATA.getPassword(), expectedPassword.pwd());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingPassword_withSameObjects_expectToBeEqual() {

    final var password = createPassword(VALID_DATA);
    final var samePassword = createPassword(VALID_DATA);

    assertEquals(password, samePassword);
    assertEquals(password.hashCode(), samePassword.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingPassword_withDifferentObjects_expectNotToBeEqual() {

    final var password = createPassword(VALID_DATA);
    final var anotherPassword = createPassword(OTHER_VALID_DATA);

    assertNotEquals(password, anotherPassword);
    assertNotEquals(password.hashCode(), anotherPassword.hashCode());
  }
}