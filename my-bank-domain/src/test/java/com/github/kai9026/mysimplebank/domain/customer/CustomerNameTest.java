package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.dummy.CustomerNameDummyData.DummyData.INVALID_DATA_INVALID_FIRSTNAME;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerNameDummyData.DummyData.INVALID_DATA_INVALID_LASTNAME;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerNameDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerNameDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerNameDummyData.createCustomerName;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerNameTest {

  @Test
  @DisplayName("Test creation with null FIRSTNAME, expected DomainValidationException")
  void createCustomerName_withNullFirstName_throwsException() {

    assertThatThrownBy(() -> createCustomerName(INVALID_DATA_INVALID_FIRSTNAME))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Firstname and lastname cannot be null");
  }

  @Test
  @DisplayName("Test creation with null LASTNAME, expected DomainValidationException")
  void createCustomerName_withNullLastName_throwsException() {

    assertThatThrownBy(() -> createCustomerName(INVALID_DATA_INVALID_LASTNAME))
        .isInstanceOf(DomainValidationException.class)
        .hasMessageContaining("Firstname and lastname cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected not null customer name")
  void createCustomerName_withValidParameters_returnObjectCreated() {

    final var expectedCustomerName = createCustomerName(VALID_DATA);

    assertNotNull(expectedCustomerName);
    assertEquals(VALID_DATA.getFirstName(), expectedCustomerName.firstName());
    assertEquals(VALID_DATA.getLastName(), expectedCustomerName.lastName());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingCustomerName_withSameObjects_expectToBeEqual() {

    final var customerName = createCustomerName(VALID_DATA);
    final var sameCustomerName = createCustomerName(VALID_DATA);

    assertEquals(customerName, sameCustomerName);
    assertEquals(sameCustomerName.hashCode(), sameCustomerName.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingCustomerName_withDifferentObjects_expectNotToBeEqual() {

    final var customerName = createCustomerName(VALID_DATA);
    final var anotherCustomerName = createCustomerName(OTHER_VALID_DATA);

    assertNotEquals(customerName, anotherCustomerName);
    assertNotEquals(customerName.hashCode(), anotherCustomerName.hashCode());
  }
}