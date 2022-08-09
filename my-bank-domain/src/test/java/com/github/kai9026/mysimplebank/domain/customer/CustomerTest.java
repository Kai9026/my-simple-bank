package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.dummy.CustomerDummyData.DummyData.INVALID_DATA_WRONG_BIRTHDATE;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerDummyData.DummyData.INVALID_DATA_WRONG_PASSWORD;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerDummyData.DummyData.OTHER_VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerDummyData.DummyData.VALID_DATA;
import static com.github.kai9026.mysimplebank.domain.dummy.CustomerDummyData.createCustomerAggregate;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

  @Test
  @DisplayName("Test creation with customer age lower than eighteen, expected IllegalArgumentException")
  void createCustomerAggregate_withNullCustomerId_throwsException() {

    assertThatThrownBy(() -> createCustomerAggregate(INVALID_DATA_WRONG_BIRTHDATE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Customer age must be greater than 18");
  }

  @Test
  @DisplayName("Test creation with weak password, expected IllegalArgumentException")
  void createCustomerAggregate_withNullFirstName_throwsException() {

    assertThatThrownBy(() -> createCustomerAggregate(INVALID_DATA_WRONG_PASSWORD))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Password format is not valid");
  }

  @Test
  @DisplayName("Test creation, expected not customer aggregate")
  void createCustomerAggregate_withValidParameters_returnObjectCreated() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);

    assertNotNull(customerAggregate);
    assertEquals(VALID_DATA.getId(), customerAggregate.id().id());
    assertEquals(VALID_DATA.getFirstName(), customerAggregate.customerFullName().firstName());
    assertEquals(VALID_DATA.getLastName(), customerAggregate.customerFullName().lastName());
    assertEquals(VALID_DATA.getStreetAddress(),
        customerAggregate.customerAddress().streetAddress());
    assertEquals(VALID_DATA.getPostalCode(), customerAggregate.customerAddress().postalCode());
    assertEquals(VALID_DATA.getCity(), customerAggregate.customerAddress().city());
    assertEquals(VALID_DATA.getBirthDate(), customerAggregate.birthDate());
    assertEquals(VALID_DATA.getNickName(), customerAggregate.nickName());
    assertEquals(VALID_DATA.getPassword(), customerAggregate.password().pwd());
    assertEquals(VALID_DATA.getEmail(), customerAggregate.emailAddress().email());
  }

  @Test
  @DisplayName("Test change EMAIL_ADDRESS with a valid one, expected new email")
  void changeEmailAddress_withValidEmail_shouldUpdateEmail() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var newEmail = "joe.cocker22@gmail.com";

    customerAggregate.changeEmail(newEmail);

    assertNotNull(customerAggregate);
    assertEquals(newEmail, customerAggregate.emailAddress().email());
  }

  @Test
  @DisplayName("Test change EMAIL_ADDRESS with an invalid one, throws exception")
  void changeEmailAddress_withInvalidEmail_throwsException() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var newEmail = "joe@gmail";

    assertThatThrownBy(() -> customerAggregate.changeEmail(newEmail))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Email format is not valid");
  }

  @Test
  @DisplayName("Test change CUSTOMER_ADDRESS with a valid one, expected new customer address")
  void changeCustomerAddress_withValidAddress_shouldUpdateCustomerAddress() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var newStreet = "Rainbow street";
    final var postalCode = 110;
    final var city = "Ohio";

    customerAggregate.changeAddress(newStreet, postalCode, city);
    assertNotNull(customerAggregate);
    assertEquals(newStreet, customerAggregate.customerAddress().streetAddress());
    assertEquals(postalCode, customerAggregate.customerAddress().postalCode());
    assertEquals(city, customerAggregate.customerAddress().city());
  }

  @Test
  @DisplayName("Test change CUSTOMER_ADDRESS with an invalid one, throws exception")
  void changeCustomerAddress_withInvalidAddress_throwsException() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var newStreet = "Rainbow street";
    final var newPostalCode = 0;
    final var newCity = "Ohio";

    assertThatThrownBy(() -> customerAggregate.changeAddress(newStreet, newPostalCode, newCity))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Postal code format is not valid");
  }

  @Test
  @DisplayName("Test change PASSWORD with a valid one, expected new password")
  void changeCustomerAddress_withValidPassword_shouldUpdatePassword() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var newPassword = "Np.dte34$20";

    customerAggregate.changePassword(newPassword);
    assertNotNull(customerAggregate);
    assertEquals(newPassword, customerAggregate.password().pwd());
  }

  @Test
  @DisplayName("Test change PASSWORD with an invalid one, throws exception")
  void changeCustomerAddress_withInvalidPassword_throwsException() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var newPassword = "123456";

    assertThatThrownBy(() -> customerAggregate.changePassword(newPassword))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Password format is not valid");
  }

  @Test
  @DisplayName("Test with same object, expected be equals")
  void comparingCustomerAggregate_withSameObject_expectToBeEqual() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var sameCustomerAggregate = createCustomerAggregate(VALID_DATA);

    assertEquals(customerAggregate, sameCustomerAggregate);
    assertEquals(customerAggregate.hashCode(), sameCustomerAggregate.hashCode());
  }

  @Test
  @DisplayName("Test with other object, expected be different")
  void comparingCustomerAggregate_withDifferentObject_expectNotToBeEqual() {

    final var customerAggregate = createCustomerAggregate(VALID_DATA);
    final var anotherCustomerAggregate = createCustomerAggregate(OTHER_VALID_DATA);

    assertNotEquals(customerAggregate, anotherCustomerAggregate);
    assertNotEquals(customerAggregate.hashCode(), anotherCustomerAggregate.hashCode());
  }
}