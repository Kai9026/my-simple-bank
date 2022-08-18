package com.github.kai9026.mysimplebank.infrastructure.database.mapper.customer;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.infrastructure.database.entity.customer.CustomerEntity;

public interface CustomerMapper {

  CustomerEntity toCustomerEntity(final Customer customer);

  Customer toCustomer(final CustomerEntity customerEntity);

}
