package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer;

import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerRegistrationRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerRegistrationResponse;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerRequest;
import static com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy.CustomerDummyData.createCustomerResourceDummy;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.RestExceptionHandler;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer.CustomerRegistrationApiMapper;
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
  private CustomerRegistrationApiMapper customerRegistrationApiMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Test customer rest controller, if no errors then return a response with created status and resource")
  void createCustomer_withValidPayload_shouldReturnResponseWithStatusCreatedAndResource()
      throws Exception {
    when(this.customerRegistrationApiMapper.toApplicationModel(
        any(CustomerCreationApiRequest.class)))
        .thenReturn(createCustomerRegistrationRequest());
    final var customerRegistrationResponse = createCustomerRegistrationResponse();
    when(this.customerRegistrationUseCase.registerNewCustomer(
        any(CustomerRegistrationRequest.class)))
        .thenReturn(customerRegistrationResponse);
    final var customerResourceDummy = createCustomerResourceDummy();
    when(
        this.customerRegistrationApiMapper.toResourceModel(any(CustomerRegistrationResponse.class)))
        .thenReturn(customerResourceDummy);

    final var customerRequest = createCustomerRequest();
    mockMvc.perform(post("/customer")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").isNotEmpty());

    verify(this.customerRegistrationUseCase, times(1))
        .registerNewCustomer(any(CustomerRegistrationRequest.class));
    verify(this.customerRegistrationApiMapper, times(1))
        .toApplicationModel(any(CustomerCreationApiRequest.class));
    verify(this.customerRegistrationApiMapper, times(1))
        .toResourceModel(any(CustomerRegistrationResponse.class));
  }

  @Test
  @DisplayName("Test customer rest controller, if duplicated customer then return a response with bad request response status")
  void createCustomer_withExistingEmail_shouldReturnResponseWithBadRequestStatus()
      throws Exception {
    when(this.customerRegistrationApiMapper.toApplicationModel(
        any(CustomerCreationApiRequest.class)))
        .thenReturn(createCustomerRegistrationRequest());
    doThrow(new DuplicateCustomerException("customer@mail.com"))
        .when(this.customerRegistrationUseCase)
        .registerNewCustomer(any(CustomerRegistrationRequest.class));

    final var customerRequest = createCustomerRequest();
    mockMvc.perform(post("/customer")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", is("err-01")))
        .andExpect(jsonPath("$.message", is("Invalid input data")))
        .andExpect(
            jsonPath("$.detail", is("Customer with email 'customer@mail.com' already exists")));

    verify(this.customerRegistrationUseCase, times(1))
        .registerNewCustomer(any(CustomerRegistrationRequest.class));
    verify(this.customerRegistrationApiMapper, times(1))
        .toApplicationModel(any(CustomerCreationApiRequest.class));
    verifyNoMoreInteractions(this.customerRegistrationApiMapper);
  }

  @Test
  @DisplayName("Test customer rest controller, if invalid data then return a response with bad request response status")
  void createCustomer_withInvalidParams_shouldReturnResponseWithBadRequestStatus()
      throws Exception {
    when(this.customerRegistrationApiMapper.toApplicationModel(
        any(CustomerCreationApiRequest.class)))
        .thenReturn(createCustomerRegistrationRequest());
    final var customerResourceDummy = createCustomerResourceDummy();
    when(
        this.customerRegistrationApiMapper.toResourceModel(any(CustomerRegistrationResponse.class)))
        .thenReturn(customerResourceDummy);
    doThrow(new InvalidInputDataException("Email cannot be null"))
        .when(this.customerRegistrationUseCase)
        .registerNewCustomer(any(CustomerRegistrationRequest.class));

    final var customerRequest = createCustomerRequest();
    mockMvc.perform(post("/customer")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", is("err-01")))
        .andExpect(jsonPath("$.message", is("Invalid input data")))
        .andExpect(jsonPath("$.detail", is("Email cannot be null")));

    verify(this.customerRegistrationUseCase, times(1))
        .registerNewCustomer(any(CustomerRegistrationRequest.class));
    verify(this.customerRegistrationApiMapper, times(1))
        .toApplicationModel(any(CustomerCreationApiRequest.class));
    verifyNoMoreInteractions(this.customerRegistrationApiMapper);

  }

}