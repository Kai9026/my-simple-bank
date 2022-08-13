package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerAddressDTO {

  @NotNull
  private String street;

  @NotNull
  private String city;

  @NotNull
  private int postalCode;

}
