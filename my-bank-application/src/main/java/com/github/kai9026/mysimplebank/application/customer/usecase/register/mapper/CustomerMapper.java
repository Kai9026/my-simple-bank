package com.github.kai9026.mysimplebank.application.customer.usecase.register.mapper;

import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.domain.customer.Customer;
import java.util.UUID;

public final class CustomerMapper {

  private CustomerMapper() {}

  public static Customer fromCustomerRegistrationRequest(final CustomerRegistrationRequest request) {
    return new Customer.Builder()
        .id(UUID.randomUUID())
        .fullName(request.getFullName().getFirstName(), request.getFullName().getLastName())
        .streetAddress(request.getAddress().getStreet(), request.getAddress().getPostalCode(),
            request.getAddress().getCity())
        .emailAddress(request.getEmail())
        .birthDate(request.getBirthDate())
        .password(request.getCustomerPassword())
        .build();
  }

  public static CustomerRegistrationResponse toCustomerRegistrationResponse(final Customer customer) {
    return new CustomerRegistrationResponse();
  }

}
