package com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer;

import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;

public interface CustomerRegistrationApiMapper {

  CustomerRegistrationRequest toApplicationModel(CustomerCreationApiRequest request);
  CustomerResource toResourceModel(CustomerRegistrationResponse response);

}
