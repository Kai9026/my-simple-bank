package com.github.kai9026.mysimplebank.infrastructure.database.mapper;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.CustomerEntity;

public interface CustomerMapper {

  CustomerEntity toCustomerEntity(final Customer customer);

  Customer toCustomer(final CustomerEntity customerEntity);

}
