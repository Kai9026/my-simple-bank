package com.github.kai9026.mysimplebank.it;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum.VALIDATION_DATA_ERROR;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.customer.repository.CustomerRepository;
import com.github.kai9026.mysimplebank.infrastructure.boot.MySimpleBankApplication;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorModel;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(classes = MySimpleBankApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRegistrationIT {

  private static final String URI = "http://localhost:8080/customer";
  private static final String EXISTING_EMAIL_IN_EMBEDDED_DATABASE = "joe@mail.com";
  private static final String INVALID_PASSWORD = "123456";

  @Autowired
  private CustomerRepository customerRepository;

  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Test
  @DisplayName("Creating a customer which email already exists should return BAD_REQUEST response with detail message")
  void createCustomer_withExistingEmail_shouldReturnResponseWithBadRequestStatus() {

    final var customerRequest = createCustomerRequest();
    customerRequest.setEmail(EXISTING_EMAIL_IN_EMBEDDED_DATABASE);

    final var apiResponse = testRestTemplate.postForEntity(URI, customerRequest,
        ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "Customer with email '" + EXISTING_EMAIL_IN_EMBEDDED_DATABASE + "' already exists",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Creating a customer with invalid parameters should return BAD_REQUEST response with detail message")
  void createCustomer_withInvalidParams_shouldReturnResponseWithBadRequestStatus() {

    final var customerRequest = createCustomerRequest();
    customerRequest.setPassword(INVALID_PASSWORD);

    final var apiResponse = testRestTemplate.postForEntity(URI, customerRequest,
        ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "Password format is not valid",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Creating a customer with valid parameters should return CREATED response and resource representation")
  void createCustomer_withValidPayload_shouldReturnResponseWithStatusCreatedAndResource() {

    final var customerRequest = createCustomerRequest();
    final var apiResponse = testRestTemplate.postForEntity(URI, customerRequest,
        CustomerResource.class);

    assertNotNull(apiResponse);
    final var customerCode = UUID.fromString(apiResponse.getBody().getCustomerCode());
    assertTrue(this.customerRepository.findById(CustomerId.fromId(customerCode)).isPresent());
  }
}
