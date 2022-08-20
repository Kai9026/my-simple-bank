package com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer;

import com.github.kai9026.mysimplebank.application.usecase.transfermoney.TransferMoneyUseCase;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.moneytransfer.model.MoneyTransferApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.mapper.moneytransfer.MoneyTransferApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moneytransfer")
@RequiredArgsConstructor
@Slf4j
public class MoneyTransferRestController {

  private final TransferMoneyUseCase transferMoneyUseCase;
  private final MoneyTransferApiMapper moneyTransferApiMapper;

  @PostMapping
  public ResponseEntity<Void> transferMoneyBetweenAccounts(
      @Validated @RequestBody MoneyTransferApiRequest request) {
    log.info(">> Transfer money between accounts with request -> {}", request);

    final var transferMoneyRequest = this.moneyTransferApiMapper.toApplicationModel(request);
    this.transferMoneyUseCase.transferMoney(transferMoneyRequest);

    return ResponseEntity.ok().build();
  }
}
