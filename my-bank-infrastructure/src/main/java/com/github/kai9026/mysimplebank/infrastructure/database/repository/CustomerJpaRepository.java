package com.github.kai9026.mysimplebank.infrastructure.database.repository;

import com.github.kai9026.mysimplebank.infrastructure.database.entity.CustomerEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

  boolean existsCustomerByEmail(String email);
  Optional<CustomerEntity> findByCustomerCode(UUID customerCode);
}
