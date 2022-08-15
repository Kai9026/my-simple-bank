package com.github.kai9026.mysimplebank.application.usecase.customer.registration;

import com.github.kai9026.mysimplebank.application.common.model.UseCase;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.usecase.customer.registration.model.CustomerRegistrationResponse;

public interface CustomerRegistrationUseCase extends UseCase {

  CustomerRegistrationResponse registerNewCustomer(
      CustomerRegistrationRequest customerRegistrationData);
}
