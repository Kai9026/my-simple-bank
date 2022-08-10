package com.github.kai9026.mysimplebank.application.customer.usecase.register.service;

import com.github.kai9026.mysimplebank.application.customer.usecase.register.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.mapper.CustomerMapper;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerRegistrationService implements CustomerRegistrationUseCase {

  private final CustomerRepository customerRepository;

  public CustomerRegistrationService(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CustomerRegistrationResponse registerNewCustomer(CustomerRegistrationRequest request) {

    final var customerEmail = request.getEmail();

    try {
      final var customerAlreadyExists = customerRepository.checkDuplicatedUserByEmail(
          customerEmail);

      return Optional.of(customerAlreadyExists)
          .filter(exists -> !exists)
          .map(notExists -> CustomerMapper.fromCustomerRegistrationRequest(request))
          .map(this.customerRepository::save)
          .map(CustomerMapper::toCustomerRegistrationResponse)
          .orElseThrow(() -> new DuplicateCustomerException(customerEmail));

    } catch (DomainValidationException iex) {
      log.error("Invalid input parameters to register customer", iex);
      throw new InvalidInputDataException(iex.getMessage());
    }

  }

}
