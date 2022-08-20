package com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.customer.CustomerEntity;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.customer.CustomerMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.CustomerJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryImplTest {

  @Mock
  private CustomerJpaRepository customerJpaRepository;

  @Mock
  private CustomerMapper customerMapper;

  @InjectMocks
  private CustomerRepositoryImpl customerRepository;

  @Test
  @DisplayName("Test check duplicated user, return true result")
  void checkingDuplicatedUser_duplicatedEmail_shouldReturnTrue() {
    when(this.customerJpaRepository.existsCustomerByEmail(anyString()))
        .thenReturn(true);

    assertTrue(this.customerRepository.checkDuplicatedCustomerByEmail("joe@mail.com"));
  }

  @Test
  @DisplayName("Test check duplicated user, return false result")
  void checkingDuplicatedUser_noDuplicatedEmail_shouldReturnTrue() {
    when(this.customerJpaRepository.existsCustomerByEmail(anyString()))
        .thenReturn(false);

    assertFalse(this.customerRepository.checkDuplicatedCustomerByEmail("joe@mail.com"));
  }

  @Test
  @DisplayName("Test save customer, jpa save method is called")
  void saveCustomer_shouldReturnSavedCustomer() {
    when(this.customerJpaRepository.save(any(CustomerEntity.class)))
        .thenReturn(mock(CustomerEntity.class));
    when(this.customerMapper.toCustomerEntity(any(Customer.class)))
        .thenReturn(mock(CustomerEntity.class));

    final var customer =
        this.customerRepository.save(CustomerDummyData.createCustomer());

    assertNotNull(customer);
    verify(this.customerMapper, times(1))
        .toCustomerEntity(any(Customer.class));
    verify(this.customerJpaRepository, times(1))
        .save(any(CustomerEntity.class));
  }

  @Test
  @DisplayName("Test get customer by id, jpa find by customer code method is called")
  void getCustomerById_shouldReturnCustomer() {
    when(this.customerJpaRepository.findByCustomerCode(any(UUID.class)))
        .thenReturn(Optional.of(mock(CustomerEntity.class)));
    when(this.customerMapper.toCustomer(any(CustomerEntity.class)))
        .thenReturn(mock(Customer.class));

    final var customer =
        this.customerRepository.findById(CustomerId.fromId(UUID.randomUUID()));

    assertNotNull(customer);
    verify(this.customerMapper, times(1))
        .toCustomer(any(CustomerEntity.class));
    verify(this.customerJpaRepository, times(1))
        .findByCustomerCode(any(UUID.class));
  }
}