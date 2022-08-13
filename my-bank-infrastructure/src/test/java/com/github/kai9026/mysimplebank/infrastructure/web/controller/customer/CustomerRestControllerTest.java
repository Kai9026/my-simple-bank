package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerRegistrationResponse;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerResourceDummy;
import static com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum.VALIDATION_DATA_ERROR;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.RestExceptionHandler;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorModel;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.CustomerRegistrationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = {CustomerRestController.class, RestExceptionHandler.class})
class CustomerRestControllerTest {

  @MockBean
  private CustomerRegistrationUseCase customerRegistrationUseCase;

  @MockBean
  private CustomerRegistrationMapper customerRegistrationMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Test customer rest controller, if no errors then return a response with created status and resource")
  void createCustomer_withValidPayload_shouldReturnResponseWithStatusCreatedAndResource()
      throws Exception {
    when(this.customerRegistrationMapper.toApplicationModel(any(CustomerRequest.class)))
        .thenReturn(mock(CustomerRegistrationRequest.class));
    final var customerRegistrationResponse = createCustomerRegistrationResponse();
    when(this.customerRegistrationUseCase.registerNewCustomer(
        any(CustomerRegistrationRequest.class)))
        .thenReturn(customerRegistrationResponse);
    final var customerResourceDummy = createCustomerResourceDummy();
    when(this.customerRegistrationMapper.toResourceModel(any(CustomerRegistrationResponse.class)))
        .thenReturn(customerResourceDummy);

    final var customerRequest = createCustomerRequest();
    final var responseResult = mockMvc.perform(post("/customer")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerRequest)))
        .andExpect(status().isCreated())
        .andReturn();

    final var responseString = responseResult.getResponse().getContentAsString();
    final var customerResource =
        objectMapper.readValue(responseString, CustomerResource.class);
    assertNotNull(customerResource);

    verify(this.customerRegistrationUseCase, times(1))
        .registerNewCustomer(any(CustomerRegistrationRequest.class));
    verify(this.customerRegistrationMapper, times(1))
        .toApplicationModel(any(CustomerRequest.class));
    verify(this.customerRegistrationMapper, times(1))
        .toResourceModel(any(CustomerRegistrationResponse.class));
  }

  @Test
  @DisplayName("Test customer rest controller, if duplicated customer then return a response with bad request response status")
  void createCustomer_withExistingEmail_shouldReturnResponseWithBadRequestStatus()
      throws Exception {
    when(this.customerRegistrationMapper.toApplicationModel(any(CustomerRequest.class)))
        .thenReturn(mock(CustomerRegistrationRequest.class));
    doThrow(new DuplicateCustomerException("customer@mail.com"))
        .when(this.customerRegistrationUseCase)
        .registerNewCustomer(any(CustomerRegistrationRequest.class));

    final var customerRequest = createCustomerRequest();
    final var responseResult = mockMvc.perform(post("/customer")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerRequest)))
        .andExpect(status().isBadRequest())
        .andReturn();

    final var responseString = responseResult.getResponse().getContentAsString();
    final var errorModel = objectMapper.readValue(responseString, ErrorModel.class);
    assertNotNull(errorModel);
    assertAll(() -> {
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), errorModel.errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), errorModel.message());
      assertEquals("Customer with email 'customer@mail.com' already exists",
          errorModel.detail());
    });
    verify(this.customerRegistrationUseCase, times(1))
        .registerNewCustomer(any(CustomerRegistrationRequest.class));
    verify(this.customerRegistrationMapper, times(1))
        .toApplicationModel(any(CustomerRequest.class));
    verifyNoMoreInteractions(this.customerRegistrationMapper);
  }

  @Test
  @DisplayName("Test customer rest controller,if invalid data then return a response with bad request response status")
  void createCustomer_withInvalidParams_shouldReturnResponseWithBadRequestStatus()
      throws Exception {
    when(this.customerRegistrationMapper.toApplicationModel(any(CustomerRequest.class)))
        .thenReturn(mock(CustomerRegistrationRequest.class));
    final var customerResourceDummy = createCustomerResourceDummy();
    when(this.customerRegistrationMapper.toResourceModel(any(CustomerRegistrationResponse.class)))
        .thenReturn(customerResourceDummy);
    doThrow(new InvalidInputDataException("Email cannot be null"))
        .when(this.customerRegistrationUseCase)
        .registerNewCustomer(any(CustomerRegistrationRequest.class));

    final var customerRequest = createCustomerRequest();
    final var responseResult = mockMvc.perform(post("/customer")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerRequest)))
        .andExpect(status().isBadRequest())
        .andReturn();

    final var responseString = responseResult.getResponse().getContentAsString();
    final var errorModel = objectMapper.readValue(responseString, ErrorModel.class);
    assertNotNull(errorModel);
    assertAll(() -> {
      assertEquals(VALIDATION_DATA_ERROR.getErrorCode(), errorModel.errorCode());
      assertEquals(VALIDATION_DATA_ERROR.getErrorMessage(), errorModel.message());
      assertEquals("Email cannot be null",
          errorModel.detail());
    });
    verify(this.customerRegistrationUseCase, times(1))
        .registerNewCustomer(any(CustomerRegistrationRequest.class));
    verify(this.customerRegistrationMapper, times(1))
        .toApplicationModel(any(CustomerRequest.class));
    verifyNoMoreInteractions(this.customerRegistrationMapper);

  }

}