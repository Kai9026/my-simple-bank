package com.github.kai9026.mysimplebank.application.usecase.customer.registration.service;

import com.github.kai9026.mysimplebank.application.usecase.customer.registration.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.mapper.CustomerRegistrationUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class CustomerRegistrationService implements CustomerRegistrationUseCase {

  private final CustomerRepository customerRepository;

  public CustomerRegistrationService(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  @Transactional
  public CustomerRegistrationResponse registerNewCustomer(CustomerRegistrationRequest request) {

    final var customerEmail = request.getEmail();

    try {
      final var customerAlreadyExists = customerRepository.checkDuplicatedUserByEmail(
          customerEmail);

      return Optional.of(customerAlreadyExists)
          .filter(exists -> !exists)
          .map(notExists -> CustomerRegistrationUseCaseMapper.fromCustomerRegistrationRequest(request))
          .map(this.customerRepository::save)
          .map(CustomerRegistrationUseCaseMapper::toCustomerRegistrationResponse)
          .orElseThrow(() -> new DuplicateCustomerException(customerEmail));

    } catch (DomainValidationException iex) {
      log.error("Invalid input parameters to register customer", iex);
      throw new InvalidInputDataException(iex.getMessage());
    }

  }

}
