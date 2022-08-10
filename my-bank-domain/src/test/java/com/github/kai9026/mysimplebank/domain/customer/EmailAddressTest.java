package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.dummy.EmailAddressDummyData.DummyData.INVALID_DATA_INVALID_EMAIL;
import static com.github.kai9026.mysimplebank.domain.dummy.EmailAddressDummyData.DummyData.INVALID_DATA_NULL_EMAIL;
import static com.github.kai9026.mysimplebank.domain.dummy.EmailAddressDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.EmailAddressDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.EmailAddressDummyData.createEmail;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailAddressTest {

  @Test
  @DisplayName("Test creation with null EMAIL, expected DomainValidationException")
  void createEmailAddress_withNullEmail_throwsException() {

    assertThatThrownBy(() -> createEmail(INVALID_DATA_NULL_EMAIL))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Email cannot be null");
  }

  @Test
  @DisplayName("Test creation with invalid EMAIL, expected DomainValidationException")
  void createEmailAddress_withInvalidEmail_throwsException() {

    assertThatThrownBy(() -> createEmail(INVALID_DATA_INVALID_EMAIL))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Email format is not valid");
  }

  @Test
  @DisplayName("Test creation, expected not null email address")
  void createEmailAddress_withValidParameters_returnObjectCreated() {

    final var expectedEmail = createEmail(VALID_DATA);

    assertNotNull(expectedEmail);
    assertEquals(VALID_DATA.getEmail(), expectedEmail.email());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingEmailAddress_withSameObjects_expectToBeEqual() {

    final var email = createEmail(VALID_DATA);
    final var sameEmail = createEmail(VALID_DATA);

    assertEquals(email, sameEmail);
    assertEquals(email.hashCode(), sameEmail.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingEmailAddress_withDifferentObjects_expectNotToBeEqual() {

    final var email = createEmail(VALID_DATA);
    final var anotherEmail = createEmail(OTHER_VALID_DATA);

    assertNotEquals(email, anotherEmail);
    assertNotEquals(email.hashCode(), anotherEmail.hashCode());
  }
}