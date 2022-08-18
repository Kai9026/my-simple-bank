package com.github.kai9026.mysimplebank.infrastructure.configuration;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.BankAccountCreationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper.BankAccountCreationUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper.impl.BankAccountCreationUseCaseMapperImpl;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.service.BankAccountCreationService;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.BankAccountDepositUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.BankAccountDepositUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.impl.BankAccountDepositUseCaseMapperImpl;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.service.BankAccountDepositService;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.service.CustomerRegistrationService;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.BankAccountTransactionMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.impl.BankAccountMapperImpl;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.bankaccount.impl.BankAccountTransactionMapperImpl;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.customer.CustomerMapper;
import com.github.kai9026.mysimplebank.infrastructure.database.mapper.customer.impl.CustomerMapperImpl;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.BankAccountJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.BankAccountTransactionJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount.impl.BankAccountRepositoryImpl;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.CustomerJpaRepository;
import com.github.kai9026.mysimplebank.infrastructure.database.repository.customer.impl.CustomerRepositoryImpl;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.BankAccountResourceMapper;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.BankAccountResourceMapperImpl;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer.CustomerRegistrationMapper;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer.impl.CustomerRegistrationMapperImpl;
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

  @Bean
  public BankAccountCreationUseCase bankAccountCreationUseCase(
      final BankAccountRepository bankAccountRepository,
      final BankAccountCreationUseCaseMapper bankAccountCreationUseCaseMapper) {
    return new BankAccountCreationService(bankAccountRepository, bankAccountCreationUseCaseMapper);
  }

  @Bean
  public BankAccountRepository bankAccountRepository(
      final BankAccountJpaRepository bankAccountJpaRepository,
      final BankAccountTransactionJpaRepository bankAccountTransactionJpaRepository,
      final BankAccountMapper bankAccountMapper,
      final BankAccountTransactionMapper bankAccountTransactionMapper) {
    return new BankAccountRepositoryImpl(bankAccountJpaRepository,
        bankAccountTransactionJpaRepository,
        bankAccountMapper,
        bankAccountTransactionMapper);
  }

  @Bean
  public BankAccountCreationUseCaseMapper bankAccountCreationUseCaseMapper() {
    return new BankAccountCreationUseCaseMapperImpl();
  }

  @Bean
  public BankAccountResourceMapper bankAccountResourceMapper() {
    return new BankAccountResourceMapperImpl();
  }

  @Bean
  public BankAccountDepositUseCase bankAccountDepositUseCase(
      final BankAccountRepository bankAccountRepository,
      final BankAccountDepositUseCaseMapper bankAccountDepositUseCaseMapper) {
    return new BankAccountDepositService(bankAccountRepository, bankAccountDepositUseCaseMapper);
  }

  @Bean
  public BankAccountDepositUseCaseMapper bankAccountDepositUseCaseMapper() {
    return new BankAccountDepositUseCaseMapperImpl();
  }

  @Bean
  public BankAccountMapper bankAccountMapper() {
    return new BankAccountMapperImpl();
  }

  @Bean
  public BankAccountTransactionMapper bankAccountTransactionMapper() {
    return new BankAccountTransactionMapperImpl();
  }

}
