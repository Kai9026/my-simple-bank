package com.github.kai9026.mysimplebank.application.usecase.customer.registration.mapper;

import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerAddress;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerFullName;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.domain.customer.Customer;
import java.util.UUID;

public final class CustomerRegistrationUseCaseMapper {

  private CustomerRegistrationUseCaseMapper() {}

  public static Customer fromCustomerRegistrationRequest(final CustomerRegistrationRequest request) {
    return new Customer.Builder()
        .id(UUID.randomUUID())
        .fullName(request.fullName().getFirstName(), request.fullName().getLastName())
        .streetAddress(request.address().getStreet(), request.address().getPostalCode(),
            request.address().getCity())
        .emailAddress(request.customerEmail())
        .birthDate(request.customerBirthDate())
        .password(request.customerPassword())
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
