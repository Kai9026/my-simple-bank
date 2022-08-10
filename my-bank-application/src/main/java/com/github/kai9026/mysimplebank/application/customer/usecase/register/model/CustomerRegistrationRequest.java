package com.github.kai9026.mysimplebank.application.customer.usecase.register.model;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import java.time.LocalDate;

public class CustomerRegistrationRequest {

  private final FullName fullName;
  private final Address address;
  private final String customerEmail;
  private final LocalDate customerBirthDate;
  private final String customerPassword;

  public CustomerRegistrationRequest(FullName fullName,
      Address address, String customerEmail, LocalDate customerBirthDate,
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

  private void validateRequiredRequestFields(FullName fullName,
      Address address,
      String customerEmail, LocalDate customerBirthDate,
      String customerPassword) {
    if (isEmptyField(fullName.firstName) || isEmptyField(fullName.lastName)) {
      throw new InvalidInputDataException("Customer firstname and lastname are required");
    }

    if (isEmptyField(address.street) || isNull(address.postalCode) || isEmptyField(
        address.city)) {
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

  public static class FullName {

    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }
  }

  public static class Address {

    private final String street;
    private final Integer postalCode;
    private final String city;

    public Address(String street, Integer postalCode, String city) {
      this.street = street;
      this.postalCode = postalCode;
      this.city = city;
    }

    public String getStreet() {
      return street;
    }

    public Integer getPostalCode() {
      return postalCode;
    }

    public String getCity() {
      return city;
    }
  }

  public FullName getFullName() {
    return fullName;
  }

  public Address getAddress() {
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
}
