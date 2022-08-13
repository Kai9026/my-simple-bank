package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CustomerNameDTO {

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;
}
