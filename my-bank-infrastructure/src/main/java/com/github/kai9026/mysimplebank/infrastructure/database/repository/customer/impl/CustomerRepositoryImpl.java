package com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.impl;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.customer.CustomerMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.CustomerJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerJpaRepository jpaRepository;
  private final CustomerMapper customerMapper;

  @Override
  public boolean checkDuplicatedUserByEmail(String email) {
    return this.jpaRepository.existsCustomerByEmail(email);
  }

  @Override
  public Customer save(Customer aggregate) {
    final var customerEntity = this.customerMapper.toCustomerEntity(aggregate);
    this.jpaRepository.save(customerEntity);

    return aggregate;
  }

  @Override
  public Optional<Customer> findById(CustomerId id) {
    return this.jpaRepository.findByCustomerCode(id.id())
        .map(this.customerMapper::toCustomer);
  }
}
