package com.github.kai9026.mysimplebank.application.customer.usecase.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationRequest.Address;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationRequest.FullName;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.service.CustomerRegistrationService;
import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.domain.customer.Customer;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CustomerRegistrationServiceTest {

  public static final String FIRSTNAME = "name";
  public static final String LASTNAME = "lastname";
  public static final String STREET = "lastname";
  public static final Integer POSTAL_CODE = 37001;
  public static final String CITY = "lastname";

  public static final String EMAIL = "customer@gmx.es";
  public static final LocalDate BIRTH_DATE = LocalDate.of(1990, 7, 9);
  public static final String PASSWORD = "Tjm$xdfo.2";
  private final CustomerRepository customerRepository = mock(CustomerRepository.class);

  private CustomerRegistrationService customerRegistrationService;

  @BeforeAll
  private void init() {
    this.customerRegistrationService = new CustomerRegistrationService(customerRepository);
  }

  @Test
  @DisplayName("Test customer registration use case, throws DuplicateCustomerException")
  void registerCustomer_withDuplicateEmailOrNick_throwsException() {

    when(this.customerRepository.checkDuplicatedUserByEmail(anyString()))
        .thenReturn(true);
    when(this.customerRepository.save(any(Customer.class)))
        .thenReturn(dummyCustomer());
    final var fullName = new FullName(FIRSTNAME, LASTNAME);
    final var address = new Address(STREET, POSTAL_CODE, CITY);
    final var request = new CustomerRegistrationRequest(fullName, address, EMAIL,
        BIRTH_DATE, PASSWORD);

    assertThatThrownBy(() -> customerRegistrationService.registerNewCustomer(request))
        .isInstanceOf(DuplicateCustomerException.class)
        .hasMessageContaining(
            "Customer with email '" + EMAIL + "' already exists");
  }

  @Test
  @DisplayName("Test customer registration use case, throws IllegalArgumentException")
  void registerCustomer_withInvalidParameters_throwsException() {

    when(this.customerRepository.checkDuplicatedUserByEmail(anyString()))
        .thenReturn(false);
    when(this.customerRepository.save(any(Customer.class)))
        .thenReturn(dummyCustomer());
    final var fullName = new FullName(FIRSTNAME, LASTNAME);
    final var address = new Address(STREET, 0, CITY);
    final var request = new CustomerRegistrationRequest(fullName, address, EMAIL,
        BIRTH_DATE, PASSWORD);

    assertThatThrownBy(() -> customerRegistrationService.registerNewCustomer(request))
        .isInstanceOf(InvalidInputDataException.class)
        .hasMessageContaining(
            "Postal code format is not valid");
  }

  @Test
  @DisplayName("Test customer registration use case, expect return registered customer")
  void registerCustomer_withValidParameters_shouldRegisterCustomer() {

    when(this.customerRepository.checkDuplicatedUserByEmail(anyString()))
        .thenReturn(false);
    when(this.customerRepository.save(any(Customer.class)))
        .thenReturn(dummyCustomer());
    final var fullName = new FullName(FIRSTNAME, LASTNAME);
    final var address = new Address(STREET, POSTAL_CODE, CITY);
    final var request = new CustomerRegistrationRequest(fullName, address, EMAIL,
        BIRTH_DATE, PASSWORD);

    final var customerRegistrationResponse = customerRegistrationService.registerNewCustomer(
        request);

    assertNotNull(customerRegistrationResponse);
  }

  private Customer dummyCustomer() {
    return new Customer.Builder()
        .id(UUID.randomUUID())
        .fullName(FIRSTNAME, LASTNAME)
        .streetAddress(STREET, POSTAL_CODE, CITY)
        .emailAddress(EMAIL)
        .birthDate(BIRTH_DATE)
        .password(PASSWORD)
        .build();
  }

}