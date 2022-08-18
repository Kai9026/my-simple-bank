package com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.service;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.BankAccountDepositUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.mapper.BankAccountDepositUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.deposit.model.BankAccountDepositRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class BankAccountDepositService implements BankAccountDepositUseCase {

  private final BankAccountRepository bankAccountRepository;
  private final BankAccountDepositUseCaseMapper bankAccountDepositUseCaseMapper;

  @Override
  @Transactional
  public BankAccountBaseResponse depositIntoAccount(BankAccountDepositRequest request) {

    try {

      final var bankAccountId = BankAccountId.fromId(request.bankAccountId());
      final var depositAmount = request.depositAmount();
      final var depositCurrency = request.depositCurrency();
      final var depositConcept = request.depositDescription();

      final var bankAccount =
          this.bankAccountRepository.findById(bankAccountId)
              .orElseThrow(() -> new InvalidInputDataException("Invalid bank account"));

      bankAccount.depositMoney(depositAmount, depositCurrency, depositConcept, bankAccount.id());

      return this.bankAccountDepositUseCaseMapper
          .toBankAccountBaseResponse(this.bankAccountRepository.update(bankAccount));

    } catch (DomainValidationException dvEx) {
      log.error("Invalid input parameters to deposit into account", dvEx);
      throw new InvalidInputDataException(dvEx.getMessage());
    }
  }
}
