package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.BankAccountCreationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.BankAccountDepositUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.BankAccountDetailUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountDepositApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountDetailResource;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.customer.CustomerRestController;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.bankaccount.BankAccountApiMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankaccount")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "BankAccount", description = "Bank Account operations")
public class BankAccountRestController {

  private final BankAccountCreationUseCase bankAccountCreationUseCase;
  private final BankAccountApiMapper bankAccountApiMapper;
  private final BankAccountDepositUseCase bankAccountDepositUseCase;
  private final BankAccountDetailUseCase bankAccountDetailUseCase;

  @PostMapping
  public ResponseEntity<BankAccountResource> createBankAccount(
      @RequestBody @Validated final BankAccountCreationApiRequest request) {
    log.info(">> Create bank account with request -> {}", request);

    final var bankAccountCreationRequest =
        new BankAccountCreationRequest(request.alias(), request.currency(),
            request.customerCode());
    final var bankAccountCreationResponse =
        this.bankAccountCreationUseCase.createNewBankAccount(bankAccountCreationRequest);

    final var bankAccountResource =
        this.bankAccountApiMapper.toBankAccountResource(bankAccountCreationResponse);
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
    log.info(">> Deposit into bank account with request -> {}", request);

    final var bankAccountDepositRequest =
        new BankAccountDepositRequest(request.depositAmount(), request.currency(), accountCode,
            request.description());
    final var bankAccountDepositResponse =
        this.bankAccountDepositUseCase.depositIntoAccount(bankAccountDepositRequest);

    final var bankAccountResource =
        this.bankAccountApiMapper.toBankAccountResource(bankAccountDepositResponse);
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

  @GetMapping("/{accountCode}")
  public ResponseEntity<BankAccountDetailResource> detailAccount(
      @PathVariable UUID accountCode,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
    log.info("Retrieving detail of bank account '{}' with from '{}' and to '{}'", accountCode,
        from, to);

    final var detailRequest = new BankAccountDetailRequest(accountCode, from, to);
    final var accountDetail = this.bankAccountDetailUseCase.getBankAccountDetail(detailRequest);

    final var accountDetailResource = new BankAccountDetailResource(accountDetail.accountBalance(),
        accountDetail.transactions());
    return ResponseEntity.ok(accountDetailResource);
  }
}
