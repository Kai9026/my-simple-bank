package com.github.kai9026.mysimplebank.infrastructure.database.repository.customer;

import com.github.kai9026.mysimplebank.infrastructure.database.entity.customer.CustomerEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

  boolean existsCustomerByEmail(String email);
  Optional<CustomerEntity> findByCustomerCode(UUID customerCode);
}
