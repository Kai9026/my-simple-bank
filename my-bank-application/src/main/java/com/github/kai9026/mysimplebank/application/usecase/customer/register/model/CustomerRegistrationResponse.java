package com.github.kai9026.mysimplebank.application.usecase.customer.register.model;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerRegistrationResponse {

  private UUID code;
  private CustomerFullName customerName;
  private CustomerAddress customerAddress;
  private String email;
  private LocalDate birthDate;

  public CustomerRegistrationResponse(UUID code, CustomerFullName customerName,
      CustomerAddress customerAddress, String email, LocalDate birthDate) {
    this.code = code;
    this.customerName = customerName;
    this.customerAddress = customerAddress;
    this.email = email;
    this.birthDate = birthDate;
  }

  public UUID getCode() {
    return code;
  }

  public void setCode(UUID code) {
    this.code = code;
  }

  public CustomerFullName getCustomerName() {
    return customerName;
  }

  public void setCustomerName(
      CustomerFullName customerName) {
    this.customerName = customerName;
  }

  public CustomerAddress getCustomerAddress() {
    return customerAddress;
  }

  public void setCustomerAddress(
      CustomerAddress customerAddress) {
    this.customerAddress = customerAddress;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }
}
