package com.github.kai9026.mysimplebank.domain.customer.repository;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.shared.Repository;

public interface CustomerRepository extends Repository<Customer, CustomerId> {

  boolean checkDuplicatedUserByEmail(String email);
}
