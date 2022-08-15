package com.github.kai9026.mysimplebank.infrastructure.configuration;

import com.github.kai9026.mysimplebank.application.usecase.customer.registration.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.service.CustomerRegistrationService;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.CustomerMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.impl.CustomerMapperImpl;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.CustomerJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.impl.CustomerRepositoryImpl;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.CustomerRegistrationMapper;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.impl.CustomerRegistrationMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public CustomerRegistrationUseCase customerRegistrationUseCase(
      final CustomerRepository customerRepository) {
    return new CustomerRegistrationService(customerRepository);
  }

  @Bean
  public CustomerMapper customerMapper() {
    return new CustomerMapperImpl();
  }

  @Bean
  public CustomerRepository customerRepository(final CustomerJpaRepository customerJpaRepository) {
    return new CustomerRepositoryImpl(customerJpaRepository, customerMapper());
  }

  @Bean
  public CustomerRegistrationMapper customerRegistrationMapper() {
    return new CustomerRegistrationMapperImpl();
  }

}
