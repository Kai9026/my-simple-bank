package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model;

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
public class BankAccountResource extends RepresentationModel<BankAccountResource> {

  private String bankAccountCode;
  private String accountNumber;
  private double accountBalance;
  private String accountAlias;
  private String accountCurrency;
  private String accountCustomerCode;

}
