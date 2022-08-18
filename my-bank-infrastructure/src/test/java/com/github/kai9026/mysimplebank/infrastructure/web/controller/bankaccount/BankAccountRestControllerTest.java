package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createBankAccountBaseResponse;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createBankAccountCreationRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createBankAccountDepositRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createBankAccountResourceDummy;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createInvalidBankAccountCreationRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.BankAccountDummyData.createInvalidBankAccountDepositRequest;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.BankAccountCreationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.BankAccountDepositUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.RestExceptionHandler;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.BankAccountResourceMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = {BankAccountRestController.class, RestExceptionHandler.class})
class BankAccountRestControllerTest {

  @MockBean
  private BankAccountCreationUseCase bankAccountCreationUseCase;

  @MockBean
  private BankAccountDepositUseCase bankAccountDepositUseCase;

  @MockBean
  private BankAccountResourceMapper bankAccountResourceMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Test bank account creation operation, if no errors then return a response with created status and resource")
  void createBankAccount_withValidPayload_shouldReturnResponseWithStatusCreatedAndResource()
      throws Exception {

    final var bankAccountBaseResponse = createBankAccountBaseResponse();
    when(
        this.bankAccountCreationUseCase.createNewBankAccount(any(BankAccountCreationRequest.class)))
        .thenReturn(bankAccountBaseResponse);

    final var bankAccountResourceDummy = createBankAccountResourceDummy();
    when(this.bankAccountResourceMapper.toBankAccountResource(any(BankAccountBaseResponse.class)))
        .thenReturn(bankAccountResourceDummy);

    final var bankAccountCreationRequest = createBankAccountCreationRequest();
    mockMvc.perform(post("/bankaccount")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(bankAccountCreationRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();

    verify(this.bankAccountCreationUseCase, times(1))
        .createNewBankAccount(any(BankAccountCreationRequest.class));
    verify(this.bankAccountResourceMapper, times(1))
        .toBankAccountResource(any(BankAccountBaseResponse.class));

  }

  @Test
  @DisplayName("Test bank account creation operation, if invalid data then return a bad request response and detail")
  void createBankAccount_withInvalidData_shouldReturnBadRequestStatusResponseAndDetail()
      throws Exception {

    final var invalidBankAccountCreationRequest = createInvalidBankAccountCreationRequest();
    mockMvc.perform(post("/bankaccount")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(invalidBankAccountCreationRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", is("err-01")))
        .andExpect(jsonPath("$.message", is("Invalid input data")))
        .andExpect(jsonPath("$.detail", is("alias: must not be null")))
        .andReturn();

    verifyNoInteractions(this.bankAccountCreationUseCase);
    verifyNoInteractions(this.bankAccountResourceMapper);
  }

  @Test
  @DisplayName("Test bank account deposit operation, if no errors then return a response with ok status and model")
  void depositIntoAccount_withValidData_shouldReturnResponseWithStatusOkAndResource()
      throws Exception {

    final var bankAccountBaseResponse = createBankAccountBaseResponse();
    when(this.bankAccountDepositUseCase.depositIntoAccount(any(BankAccountDepositRequest.class)))
        .thenReturn(bankAccountBaseResponse);
    final var bankAccountResourceDummy = createBankAccountResourceDummy();
    when(this.bankAccountResourceMapper.toBankAccountResource(any(BankAccountBaseResponse.class)))
        .thenReturn(bankAccountResourceDummy);

    final var bankAccountDepositRequest = createBankAccountDepositRequest();
    mockMvc.perform(
            post("/bankaccount/" + UUID.randomUUID() + "/deposit")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bankAccountDepositRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn();

    verify(this.bankAccountDepositUseCase, times(1))
        .depositIntoAccount(any(BankAccountDepositRequest.class));
    verify(this.bankAccountResourceMapper, times(1))
        .toBankAccountResource(any(BankAccountBaseResponse.class));
  }

  @Test
  @DisplayName("Test bank account deposit operation, if account is invalid then return bad request response and detail message")
  void depositIntoAccount_withInvalidAccount_shouldReturnBadRequestResponseAndDetailMessage()
      throws Exception {

    doThrow(new InvalidInputDataException("Invalid account")).when(this.bankAccountDepositUseCase)
        .depositIntoAccount(any(BankAccountDepositRequest.class));

    final var bankAccountDepositRequest = createBankAccountDepositRequest();
    mockMvc.perform(
            post("/bankaccount/" + UUID.randomUUID() + "/deposit")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bankAccountDepositRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", is("err-01")))
        .andExpect(jsonPath("$.message", is("Invalid input data")))
        .andExpect(jsonPath("$.detail", is("Invalid account")))
        .andReturn();

    verify(this.bankAccountDepositUseCase, times(1))
        .depositIntoAccount(any(BankAccountDepositRequest.class));
    verifyNoInteractions(this.bankAccountResourceMapper);
  }

  @Test
  @DisplayName("Test bank account deposit operation, if invalid data then return a bad request response and detail")
  void depositIntoAccount_withInvalidData_shouldReturnBadRequestResponseAndDetailMessage()
      throws Exception {

    final var invalidBankAccountDepositRequest = createInvalidBankAccountDepositRequest();
    mockMvc.perform(
            post("/bankaccount/" + UUID.randomUUID() + "/deposit")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidBankAccountDepositRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", is("err-01")))
        .andExpect(jsonPath("$.message", is("Invalid input data")))
        .andExpect(jsonPath("$.detail", is("currency: must not be null")))
        .andReturn();

    verifyNoInteractions(this.bankAccountDepositUseCase);
    verifyNoInteractions(this.bankAccountResourceMapper);
  }

}