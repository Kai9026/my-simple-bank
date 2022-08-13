package com.github.kai9026.mysimplebank.infrastructure.database.mapper.impl;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.CustomerEntity;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.CustomerMapper;
import org.mindrot.jbcrypt.BCrypt;

public class CustomerMapperImpl implements CustomerMapper {

  @Override
  public CustomerEntity toCustomerEntity(Customer customer) {
    final var customerEntity = new CustomerEntity();
    customerEntity.setCustomerCode(customer.id().id());
    customerEntity.setFirstname(customer.customerFullName().firstName());
    customerEntity.setLastname(customer.customerFullName().lastName());
    customerEntity.setStreet(customer.customerAddress().streetAddress());
    customerEntity.setCity(customer.customerAddress().city());
    customerEntity.setPostalCode(customer.customerAddress().postalCode());
    customerEntity.setEmail(customer.emailAddress().email());
    customerEntity.setBirthdate(customer.birthDate());
    customerEntity.setPassword(BCrypt.hashpw(customer.password().pwd(), BCrypt.gensalt()));

    return customerEntity;
  }

  @Override
  public Customer toCustomer(CustomerEntity customerEntity) {
    return new Customer.Builder()
        .id(customerEntity.getCustomerCode())
        .fullName(customerEntity.getFirstname(), customerEntity.getLastname())
        .streetAddress(customerEntity.getStreet(), customerEntity.getPostalCode(),
            customerEntity.getCity())
        .emailAddress(customerEntity.getEmail())
        .birthDate(customerEntity.getBirthdate())
        .password(customerEntity.getPassword()).build();
  }
}
