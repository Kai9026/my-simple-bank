package com.github.kai9026.mysimplebank.domain.customer.id;

import static com.github.kai9026.mysimplebank.domain.dummy.customer.id.CustomerIdDummyData.DummyData.INVALID_DATA_NULL_UUID;
import static com.github.kai9026.mysimplebank.domain.dummy.customer.id.CustomerIdDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.customer.id.CustomerIdDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.customer.id.CustomerIdDummyData.createCustomerId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerIdTest {

  @Test
  @DisplayName("Test creation with null CUSTOMER ID, expected IllegalArgumentException")
  void createCustomerId_withNullId_throwsException() {

    assertThatThrownBy(() -> createCustomerId(INVALID_DATA_NULL_UUID))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CustomerId cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected not null customerId")
  void createCustomerId_withValidParameters_returnObjectCreated() {

    final var expectedCustomerId = createCustomerId(VALID_DATA);

    assertNotNull(expectedCustomerId);
    assertEquals(VALID_DATA.getId(), expectedCustomerId.id());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingCustomerId_withSameObjects_expectToBeEqual() {

    final var customerId = createCustomerId(VALID_DATA);
    final var sameCustomerId = createCustomerId(VALID_DATA);

    assertEquals(customerId, sameCustomerId);
    assertEquals(customerId.hashCode(), sameCustomerId.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingCustomerId_withDifferentObjects_expectNotToBeEqual() {

    final var customerId = createCustomerId(VALID_DATA);
    final var anotherCustomerId = createCustomerId(OTHER_VALID_DATA);

    assertNotEquals(customerId, anotherCustomerId);
    assertNotEquals(customerId.hashCode(), anotherCustomerId.hashCode());
  }

}