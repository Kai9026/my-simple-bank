package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.github.kai9026.mysimplebank.application.usecase.customer.registration.CustomerRegistrationUseCase;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model.CustomerResource;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.customer.CustomerRegistrationApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerRestController {

  private final CustomerRegistrationUseCase customerRegistrationUseCase;
  private final CustomerRegistrationApiMapper customerRegistrationApiMapper;

  @PostMapping
  public ResponseEntity<CustomerResource> createCustomer(
      @RequestBody @Validated final CustomerCreationApiRequest request) {
    log.info(">> Create customer operation with request -> {}", request);

    final var customerRegistrationRequest =
        this.customerRegistrationApiMapper.toApplicationModel(request);

    final var customerRegistrationResponse =
        this.customerRegistrationUseCase.registerNewCustomer(customerRegistrationRequest);

    final var customerResource =
        this.customerRegistrationApiMapper.toResourceModel(customerRegistrationResponse);
    final var link = linkTo(CustomerRestController.class)
        .slash(customerRegistrationResponse.getCode())
        .withSelfRel();
    customerResource.add(link);

    return ResponseEntity.created(link.toUri()).body(customerResource);
  }

}
