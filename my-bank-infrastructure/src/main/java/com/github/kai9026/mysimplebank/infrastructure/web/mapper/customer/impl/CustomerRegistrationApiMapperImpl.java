package com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer.impl;

import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerAddress;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerFullName;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerAddressDTO;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerNameDTO;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer.CustomerRegistrationApiMapper;

public class CustomerRegistrationApiMapperImpl implements CustomerRegistrationApiMapper {

  @Override
  public CustomerRegistrationRequest toApplicationModel(CustomerCreationApiRequest request) {
    final var fullName =
        new CustomerFullName(request.getName().getFirstName(), request.getName().getLastName());
    final var address =
        new CustomerAddress(request.getAddress().getStreet(), request.getAddress().getPostalCode(),
            request.getAddress().getCity());
    return new CustomerRegistrationRequest(fullName, address, request.getEmail(),
        request.getBirthDate(), request.getPassword());
  }

  @Override
  public CustomerResource toResourceModel(CustomerRegistrationResponse response) {
    return CustomerResource.builder()
        .customerCode(response.getCode().toString())
        .name(new CustomerNameDTO(response.getCustomerName().getFirstName(),
            response.getCustomerName().getLastName()))
        .address(new CustomerAddressDTO(response.getCustomerAddress().getStreet(),
            response.getCustomerAddress().getCity(),
            response.getCustomerAddress().getPostalCode()))
        .email(response.getEmail())
        .birthDate(response.getBirthDate().toString()).build();
  }
}
