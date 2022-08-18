package com.github.kai9026.mysimplebank.it;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createBankAccountDepositRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createInvalidBankAccountDepositRequest;
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
class BankAccountDepositIT {

  private static final String URI = "http://localhost:8080/bankaccount";
  private static final String EXISTING_ACCOUNT = "72b4849d-2aa8-430d-a4f7-4ad70be81615";

  @Autowired
  private BankAccountRepository bankAccountRepository;

  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Test
  @DisplayName("Deposit into a bank account with invalid account code return BAD_REQUEST response with detail message")
  void depositIntoAccount_withInvalidAccount_shouldReturnResponseWithBadRequestStatus() {

    final var uri = URI.concat("/" + UUID.randomUUID() + "/deposit");
    final var request = createBankAccountDepositRequest();

    final var apiResponse = testRestTemplate.postForEntity(uri, request,
        ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "Invalid bank account",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Deposit into a bank account with invalid params return BAD_REQUEST response with detail message")
  void depositIntoAccount_withInvalidParams_shouldReturnResponseWithBadRequestStatus() {

    final var uri = URI.concat("/" + UUID.randomUUID() + "/deposit");
    final var request = createInvalidBankAccountDepositRequest();

    final var apiResponse = testRestTemplate.postForEntity(uri, request,
        ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "currency: must not be null",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Deposit into a bank account with no errors should return OK response with resource")
  void depositIntoAccount_withValidParams_shouldReturnResponseWithOkStatusAndResource() {

    final var uri = URI.concat("/" + EXISTING_ACCOUNT + "/deposit");
    final var requestWithDepositOfTen = createBankAccountDepositRequest();

    final var apiResponse = testRestTemplate.postForEntity(uri, requestWithDepositOfTen,
        BankAccountResource.class);

    assertNotNull(apiResponse);
    final var id = UUID.fromString(apiResponse.getBody().getBankAccountCode());
    assertTrue(this.bankAccountRepository.findById(BankAccountId.fromId(id)).isPresent());
    assertEquals(10.00, apiResponse.getBody().getAccountBalance()); // initial 0 + 10 of deposit
  }

}
