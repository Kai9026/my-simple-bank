package com.github.kai9026.mysimplebank.it;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createBankAccountCreationRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createInvalidBankAccountCreationRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createInvalidBankAccountInvalidCustomer;
import static com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum.VALIDATION_DATA_ERROR;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.infrastructure.boot.MySimpleBankApplication;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;
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
class BankAccountCreationIT {

  private static final String URI = "http://localhost:8080/bankaccount";

  @Autowired
  private BankAccountRepository bankAccountRepository;

  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Test
  @DisplayName("Creating a bank account with invalid parameters should return BAD_REQUEST response with detail message")
  void createBankAccount_withInvalidParams_shouldReturnResponseWithBadRequestStatus() {

    final var createBankAccountRequest = createInvalidBankAccountCreationRequest();

    final var apiResponse = testRestTemplate.postForEntity(URI, createBankAccountRequest,
        ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "alias: must not be null",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Creating a bank account with invalid customer code should return BAD_REQUEST response with detail message")
  void createBankAccount_withInvalidCustomerCode_shouldReturnResponseWithBadRequestStatus() {
    final var createBankAccountRequest = createInvalidBankAccountInvalidCustomer();

    final var apiResponse = testRestTemplate.postForEntity(URI, createBankAccountRequest,
        ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "Invalid customer",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Creating a bank account with no errors should return CREATED response with resource")
  void createBankAccount_withValidParams_shouldReturnResponseWithCreatedtStatusAndResource() {

    final var createBankAccountRequest = createBankAccountCreationRequest();

    final var apiResponse = testRestTemplate.postForEntity(URI, createBankAccountRequest,
        BankAccountResource.class);

    assertNotNull(apiResponse);
    final var id = UUID.fromString(apiResponse.getBody().getBankAccountCode());
    final var bankAccount =
        this.bankAccountRepository.findById(BankAccountId.fromId(id));
    assertTrue(bankAccount.isPresent());

  }
}
