package com.github.kai9026.mysimplebank.infrastructure.web.mapper;

import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.register.model.CustomerRegistrationResponse;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;

public interface CustomerRegistrationMapper {

  CustomerRegistrationRequest toApplicationModel(CustomerRequest request);
  CustomerResource toResourceModel(CustomerRegistrationResponse response);

}
