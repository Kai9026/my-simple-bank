package com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.MoneyTransferDummyData.createMoneyTransferRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.MoneyTransferDummyData.createTransferMoneyRequest;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.transfermoney.TransferMoneyUseCase;
import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer.model.MoneyTransferApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.RestExceptionHandler;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.moneytransfer.MoneyTransferApiMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = {MoneyTransferRestController.class, RestExceptionHandler.class})
class MoneyTransferRestControllerTest {

  private static final String MONEY_TRANSFER_URI = "/moneytransfer";

  @MockBean
  private TransferMoneyUseCase transferMoneyUseCase;

  @MockBean
  private MoneyTransferApiMapper moneyTransferApiMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;


  @Test
  @DisplayName("Test money transfer rest controller, with invalid params return bad request and detail message")
  void transferMoney_withInvalidParams_shouldReturnBadRequestAndDetail()
      throws Exception {
    when(this.moneyTransferApiMapper.toApplicationModel(any(MoneyTransferApiRequest.class)))
        .thenReturn(createTransferMoneyRequest());
    doThrow(new InvalidInputDataException("Invalid source account")).when(this.transferMoneyUseCase)
        .transferMoney(any(TransferMoneyRequest.class));

    final var moneyTransferRequest = createMoneyTransferRequest();
    mockMvc.perform(post(MONEY_TRANSFER_URI)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(moneyTransferRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", is("err-01")))
        .andExpect(jsonPath("$.message", is("Invalid input data")))
        .andExpect(jsonPath("$.detail", is("Invalid source account")));

    verify(this.moneyTransferApiMapper, times(1))
        .toApplicationModel(any(MoneyTransferApiRequest.class));
    verify(this.transferMoneyUseCase, times(1))
        .transferMoney(any(TransferMoneyRequest.class));
  }

  @Test
  @DisplayName("Test money transfer rest controller, with valid parameters then return ok response")
  void transferMoney_withValidParameters_shouldReturnOkResponse()
      throws Exception {
    when(this.moneyTransferApiMapper.toApplicationModel(any(MoneyTransferApiRequest.class)))
        .thenReturn(createTransferMoneyRequest());
    doNothing().when(this.transferMoneyUseCase).transferMoney(any(TransferMoneyRequest.class));

    final var moneyTransferRequest = createMoneyTransferRequest();
    mockMvc.perform(post(MONEY_TRANSFER_URI)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(moneyTransferRequest)))
        .andExpect(status().isOk());
  }
}