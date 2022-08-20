package com.github.kai9026.mysimplebank.it;

import static com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum.VALIDATION_DATA_ERROR;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.kai9026.mysimplebank.infrastructure.boot.MySimpleBankApplication;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountDetailResource;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorModel;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(classes = MySimpleBankApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class BankAccountGetDetailIT {

  private static final String VALID_ACCOUNT = "94c14699-b653-4516-9dc1-df7d013e5101";
  private static final String BASE_URI =
      "http://localhost:8080/bankaccount/";

  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Test
  @DisplayName("Get the detail of a bank account with invalid account return BAD_REQUEST response with detail message")
  void getBankAccountDetail_withInvalidAccount_shouldReturnBadRequestResponseAndDetailMessage() {

    URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URI.concat(UUID.randomUUID().toString()))
        .queryParam("from", "2022-07-01")
        .queryParam("to", "2022-09-01").build().toUri();
    final var apiResponse =
        testRestTemplate.getForEntity(uri, ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "Bank account invalid",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Get the detail of a bank account with invalid interval dates return BAD_REQUEST response with detail message")
  void getBankAccountDetail_withInvalidIntervalDates_shouldReturnBadRequestResponseAndDetailMessage() {

    URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URI.concat(VALID_ACCOUNT))
        .queryParam("from", "2022-06-01")
        .queryParam("to", "2022-06-29").build().toUri();
    final var apiResponse =
        testRestTemplate.getForEntity(uri, ErrorModel.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), apiResponse.getBody().errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), apiResponse.getBody().message());
      assertEquals(
          "Cannot retrieve transactions in interval. Please, contact with your bank",
          apiResponse.getBody().detail());
    });
  }

  @Test
  @DisplayName("Get the detail of a bank account with valid params return OK response and resource with filtered transactions")
  void getBankAccountDetail_withValidParams_shouldReturnOkResponseAndFilteredTransactions() {

    URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URI.concat(VALID_ACCOUNT))
        .queryParam("from", "2022-08-01")
        .queryParam("to", "2022-09-01").build().toUri();
    final var apiResponse =
        testRestTemplate.getForEntity(uri, BankAccountDetailResource.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
      assertEquals(30.00, apiResponse.getBody().accountBalance());
      assertEquals(2, apiResponse.getBody().transactionsInInterval().size());
    });
  }

  @Test
  @DisplayName("Get the detail of a bank account with valid params return OK response and all transactions in interval")
  void getBankAccountDetail_withValidParams_shouldReturnOkResponseAndAllTransactionsInInterval() {
    URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URI.concat(VALID_ACCOUNT))
        .queryParam("from", "2022-08-01")
        .queryParam("to", "2022-12-01").build().toUri();
    final var apiResponse =
        testRestTemplate.getForEntity(uri, BankAccountDetailResource.class);

    assertAll(() -> {
      assertNotNull(apiResponse);
      assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
      assertEquals(30.00, apiResponse.getBody().accountBalance());
      assertEquals(3, apiResponse.getBody().transactionsInInterval().size());
    });
  }

}
