package com.github.kai9026.mysimplebank.application.usecase.customer.registration.model;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.time.LocalDate;

public class CustomerRegistrationRequest {

  private CustomerFullName fullName;
  private CustomerAddress address;
  private String customerEmail;
  private LocalDate customerBirthDate;
  private String customerPassword;

  public CustomerRegistrationRequest(CustomerFullName fullName,
      CustomerAddress address, String customerEmail, LocalDate customerBirthDate,
       String customerPassword) {
    this.validateRequiredRequestFields(fullName, address, customerEmail,
        customerBirthDate,
        customerPassword);

    this.fullName = fullName;
    this.address = address;
    this.customerEmail = customerEmail;
    this.customerBirthDate = customerBirthDate;
    this.customerPassword = customerPassword;
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

  public CustomerFullName getFullName() {
    return fullName;
  }

  public CustomerAddress getAddress() {
    return address;
  }

  public String getEmail() {
    return customerEmail;
  }

  public LocalDate getBirthDate() {
    return customerBirthDate;
  }

  public String getCustomerPassword() {
    return customerPassword;
  }

  public void setFullName(
      CustomerFullName fullName) {
    this.fullName = fullName;
  }

  public void setAddress(
      CustomerAddress address) {
    this.address = address;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public void setCustomerBirthDate(LocalDate customerBirthDate) {
    this.customerBirthDate = customerBirthDate;
  }

  public void setCustomerPassword(String customerPassword) {
    this.customerPassword = customerPassword;
  }

  @Override
  public String toString() {
    return "CustomerRegistrationRequest{" +
        "fullName=" + fullName +
        ", address=" + address +
        ", customerEmail='" + customerEmail + '\'' +
        ", customerBirthDate=" + customerBirthDate +
        ", customerPassword='" + customerPassword + '\'' +
        '}';
  }
}
