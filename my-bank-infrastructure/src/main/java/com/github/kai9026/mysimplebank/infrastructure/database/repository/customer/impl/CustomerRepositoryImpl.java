package com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.impl;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.customer.CustomerMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.CustomerJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerJpaRepository jpaRepository;
  private final CustomerMapper customerMapper;

  @Override
  public boolean checkDuplicatedCustomerByEmail(String email) {
    log.debug("Checking if exists previous customer by email -> {} ", email);
    return this.jpaRepository.existsCustomerByEmail(email);
  }

  @Override
  public Customer save(Customer customerAggregate) {
    log.debug("Saving customer -> {}", customerAggregate);
    final var customerEntity = this.customerMapper.toCustomerEntity(customerAggregate);
    this.jpaRepository.save(customerEntity);

    return customerAggregate;
  }

  @Override
  public Optional<Customer> findById(CustomerId id) {
    log.debug("Retrieving customer by id -> {}", id);
    return this.jpaRepository.findByCustomerCode(id.id())
        .map(this.customerMapper::toCustomer);
  }
}
