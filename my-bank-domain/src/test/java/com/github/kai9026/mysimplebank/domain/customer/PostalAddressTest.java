package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.DummyData.INVALID_DATA_INVALID_POSTAL_CODE;
import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.DummyData.INVALID_DATA_NULL_CITY;
import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.DummyData.INVALID_DATA_NULL_POSTAL_CODE;
import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.DummyData.INVALID_DATA_NULL_STREET_ADDRESS;
import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.PostalAddressDummyData.createPostalAddressDummy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostalAddressTest {

  @Test
  @DisplayName("Test creation with null STREET_ADDRESS, expected IllegalArgumentException")
  void createPostalAddress_withNullStreetAddress_throwsException() {

    assertThatThrownBy(() -> createPostalAddressDummy(INVALID_DATA_NULL_STREET_ADDRESS))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Street address, postal code and city cannot be null");
  }

  @Test
  @DisplayName("Test creation with null POSTAL_CODE, expected IllegalArgumentException")
  void createPostalAddress_withNullPostalCode_throwsException() {

    assertThatThrownBy(() -> createPostalAddressDummy(INVALID_DATA_NULL_POSTAL_CODE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Street address, postal code and city cannot be null");
  }

  @Test
  @DisplayName("Test creation with invalid POSTAL_CODE, expected IllegalArgumentException")
  void createPostalAddress_withInvalidPostalCode_throwsException() {

    assertThatThrownBy(() -> createPostalAddressDummy(INVALID_DATA_INVALID_POSTAL_CODE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Postal code format is not valid");
  }

  @Test
  @DisplayName("Test creation with null CITY, expected IllegalArgumentException")
  void createPostalAddress_withNullCity_throwsException() {

    assertThatThrownBy(() -> createPostalAddressDummy(INVALID_DATA_NULL_CITY))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Street address, postal code and city cannot be null");
  }

  @Test
  @DisplayName("Test creation, expected not null postal address")
  void createPostalAddress_withValidParameters_returnObjectCreated() {

    final var expectedPostalAddress = createPostalAddressDummy(VALID_DATA);

    assertNotNull(expectedPostalAddress);
    assertEquals(VALID_DATA.getStreetAddress(), expectedPostalAddress.streetAddress());
    assertEquals(VALID_DATA.getPostalCode(), expectedPostalAddress.postalCode());
    assertEquals(VALID_DATA.getCity(), expectedPostalAddress.city());
  }

  @Test
  @DisplayName("Test with same object, expected be same objects")
  void comparingPostalAddresses_withSameObjects_expectToBeEqual() {

    final var postalAddress = createPostalAddressDummy(VALID_DATA);
    final var samePostalAddress = createPostalAddressDummy(VALID_DATA);

    assertEquals(postalAddress, samePostalAddress);
    assertEquals(postalAddress.hashCode(), samePostalAddress.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different objects")
  void comparingPostalAddresses_withDifferentObjects_expectNotToBeEqual() {

    final var postalAddress = createPostalAddressDummy(VALID_DATA);
    final var anotherPostalAddress = createPostalAddressDummy(OTHER_VALID_DATA);

    assertNotEquals(postalAddress, anotherPostalAddress);
    assertNotEquals(postalAddress.hashCode(), anotherPostalAddress.hashCode());
  }
}