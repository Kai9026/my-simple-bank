package com.github.kai9026.mysimplebank.application.usecase.customer.registration.model;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.time.LocalDate;

public record CustomerRegistrationRequest(CustomerFullName fullName, CustomerAddress address,
                                          String customerEmail, LocalDate customerBirthDate,
                                          String customerPassword) {

  public CustomerRegistrationRequest {
    this.validateRequiredRequestFields(fullName, address, customerEmail,
        customerBirthDate,
        customerPassword);
  }

  private void validateRequiredRequestFields(CustomerFullName fullName,
      CustomerAddress address,
      String customerEmail, LocalDate customerBirthDate,
      String customerPassword) {
    if (isEmptyField(fullName.getFirstName()) || isEmptyField(fullName.getLastName())) {
      throw new InvalidInputDataException("Customer firstname and lastname are required");
    }

    if (isEmptyField(address.getStreet()) || isNull(address.getPostalCode()) || isEmptyField(
        address.getCity())) {
      throw new InvalidInputDataException(
          "Address, postal code and city of customer address are required");
    }

    if (isEmptyField(customerEmail)) {
      throw new InvalidInputDataException("Customer email is required");
    }

    if (isNull(customerBirthDate)) {
      throw new InvalidInputDataException("Customer birthdate is required");
    }

    if (isEmptyField(customerPassword)) {
      throw new InvalidInputDataException("Customer nick is required");
    }
  }

}
