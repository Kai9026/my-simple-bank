package com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy;

import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerAddress;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerFullName;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerAddressDTO;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerNameDTO;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;
import java.time.LocalDate;
import java.util.UUID;

public final class CustomerDummyData {

  private static final String NAME = "Joe";
  private static final String LASTNAME = "Lewis";
  private static final String STREET = "My street, 20";
  private static final String CITY = "Ohio";
  private static final int POSTAL_CODE = 410;
  private static final String EMAIL = "mybox@gmail.com";
  private static final LocalDate BIRTHDATE = LocalDate.of(1954, 2, 1);
  private static final String PASSWORD = "Djx$20.a2df";

  private CustomerDummyData() {
  }

  public static CustomerRegistrationRequest createCustomerRegistrationRequest() {
    final var fullName = new CustomerFullName(NAME, LASTNAME);
    final var address = new CustomerAddress(STREET, POSTAL_CODE, CITY);
    return new CustomerRegistrationRequest(fullName, address, EMAIL, BIRTHDATE, PASSWORD);
  }

  public static CustomerRegistrationResponse createCustomerRegistrationResponse() {
    final var fullName = new CustomerFullName(NAME, LASTNAME);
    final var address = new CustomerAddress(STREET, POSTAL_CODE, CITY);
    return new CustomerRegistrationResponse(UUID.randomUUID(), fullName, address, EMAIL, BIRTHDATE);
  }

  public static CustomerRequest createCustomerRequest() {
    final var name = new CustomerNameDTO(NAME, LASTNAME);
    final var address = new CustomerAddressDTO(STREET, CITY, POSTAL_CODE);
    return new CustomerRequest(name, address, EMAIL, BIRTHDATE, PASSWORD);
  }

  public static CustomerResource createCustomerResourceDummy() {
    final var name = new CustomerNameDTO(NAME, LASTNAME);
    final var address = new CustomerAddressDTO(STREET, CITY, POSTAL_CODE);
    return CustomerResource.builder()
        .customerCode(UUID.randomUUID().toString())
        .name(name)
        .address(address)
        .email(EMAIL)
        .birthDate(BIRTHDATE.toString()).build();
  }

  public static Customer createCustomer() {
    return new Customer.Builder()
        .id(UUID.randomUUID())
        .fullName(NAME, LASTNAME)
        .streetAddress(STREET, POSTAL_CODE, CITY)
        .emailAddress(EMAIL)
        .birthDate(LocalDate.of(1990, 7, 2))
        .password("Dt8$23.sts")
        .build();
  }
}
