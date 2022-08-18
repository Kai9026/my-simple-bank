package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.BankAccountCreationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.BankAccountDepositUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountDepositApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.CustomerRestController;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.BankAccountResourceMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankaccount")
@RequiredArgsConstructor
@Slf4j
public class BankAccountRestController {

  private final BankAccountCreationUseCase bankAccountCreationUseCase;
  private final BankAccountResourceMapper bankAccountResourceMapper;
  private final BankAccountDepositUseCase bankAccountDepositUseCase;

  @PostMapping
  public ResponseEntity<BankAccountResource> createBankAccount(
      @RequestBody @Validated final BankAccountCreationApiRequest request) {

    final var bankAccountCreationRequest =
        new BankAccountCreationRequest(request.alias(), request.currency(),
            request.customerCode());
    final var bankAccountCreationResponse =
        this.bankAccountCreationUseCase.createNewBankAccount(bankAccountCreationRequest);

    final var bankAccountResource =
        this.bankAccountResourceMapper.toBankAccountResource(bankAccountCreationResponse);
    final var bankAccountLink = linkTo(BankAccountRestController.class)
        .slash(bankAccountCreationResponse.bankAccountCode())
        .withSelfRel();
    final var customerLink = linkTo(CustomerRestController.class)
        .slash(bankAccountCreationResponse.accountCustomerCode())
        .withRel("customer");
    bankAccountResource.add(bankAccountLink);
    bankAccountResource.add(customerLink);

    return ResponseEntity.created(bankAccountLink.toUri()).body(bankAccountResource);
  }

  @PostMapping("/{accountCode}/deposit")
  public ResponseEntity<BankAccountResource> depositToAccount(
      @PathVariable UUID accountCode,
      @RequestBody @Validated final BankAccountDepositApiRequest request) {

    final var bankAccountDepositRequest =
        new BankAccountDepositRequest(request.depositAmount(), request.currency(), accountCode,
            request.description());
    final var bankAccountDepositResponse =
        this.bankAccountDepositUseCase.depositIntoAccount(bankAccountDepositRequest);

    final var bankAccountResource =
        this.bankAccountResourceMapper.toBankAccountResource(bankAccountDepositResponse);
    final var bankAccountLink = linkTo(BankAccountRestController.class)
        .slash(bankAccountDepositResponse.bankAccountCode())
        .withSelfRel();
    final var customerLink = linkTo(CustomerRestController.class)
        .slash(bankAccountDepositResponse.accountCustomerCode())
        .withRel("customer");
    bankAccountResource.add(bankAccountLink);
    bankAccountResource.add(customerLink);

    return ResponseEntity.ok(bankAccountResource);
  }
}
