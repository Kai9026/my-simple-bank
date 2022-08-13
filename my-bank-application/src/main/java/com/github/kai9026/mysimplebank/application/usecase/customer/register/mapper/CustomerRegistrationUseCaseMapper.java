package com.github.kai9026.mysimplebank.application.usecase.customer.register.mapper;

import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerAddress;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerFullName;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.domain.customer.Customer;
import java.util.UUID;

public final class CustomerRegistrationUseCaseMapper {

  private CustomerRegistrationUseCaseMapper() {}

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
    final var customerName =
        new CustomerFullName(customer.customerFullName().firstName(),
            customer.customerFullName().lastName());
    final var customerAddress =
        new CustomerAddress(customer.customerAddress().streetAddress(),
            customer.customerAddress().postalCode(), customer.customerAddress().city());

    return new CustomerRegistrationResponse(customer.id().id() ,customerName, customerAddress,
        customer.emailAddress().email(), customer.birthDate());
  }

}
