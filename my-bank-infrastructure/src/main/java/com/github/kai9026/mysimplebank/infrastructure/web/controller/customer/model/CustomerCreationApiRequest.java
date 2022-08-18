package com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerCreationApiRequest {

  @NotNull
  private CustomerNameDTO name;

  @NotNull
  private CustomerAddressDTO address;

  @NotNull
  private String email;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @NotNull
  private String password;

}
