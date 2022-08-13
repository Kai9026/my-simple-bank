package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CustomerResource extends RepresentationModel<CustomerResource> {

  private String customerCode;
  private CustomerNameDTO name;
  private CustomerAddressDTO address;
  private String email;
  private String birthDate;

}
