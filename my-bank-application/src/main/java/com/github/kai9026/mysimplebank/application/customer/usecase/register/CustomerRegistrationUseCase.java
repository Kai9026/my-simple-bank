package com.github.kai9026.mysimplebank.application.customer.usecase.register;

import com.github.kai9026.mysimplebank.application.common.model.UseCase;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationRequest;
import com.github.kai9026.mysimplebank.application.customer.usecase.register.model.CustomerRegistrationResponse;

public interface CustomerRegistrationUseCase extends UseCase {

  CustomerRegistrationResponse registerNewCustomer(
      CustomerRegistrationRequest customerRegistrationData);
}
