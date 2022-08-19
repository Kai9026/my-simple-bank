package com.github.kai9026.mysimplebank.application.usecase.transfermoney.service;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.transfermoney.TransferMoneyUseCase;
import com.github.kai9026.mysimplebank.application.usecase.transfermoney.model.TransferMoneyRequest;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class TransferMoneyService implements TransferMoneyUseCase {

  private final BankAccountRepository bankAccountRepository;

  @Override
  @Transactional
  public void transferMoney(TransferMoneyRequest request) {

    final var sourceAccountId = request.sourceAccount();
    final var targetAccountId = request.targetAccount();
    final var transferAmount = request.transferAmount();
    final var transferCurrency = request.transferCurrency();
    final var transferConcept = request.transferConcept();

    final var sourceBankAccount = this.bankAccountRepository.findById(
            BankAccountId.fromId(sourceAccountId))
        .orElseThrow(() -> new InvalidInputDataException("Source account is invalid"));

    final var targetBankAccount = this.bankAccountRepository.findById(
            BankAccountId.fromId(targetAccountId))
        .orElseThrow(() -> new InvalidInputDataException("Target account is invalid"));

    if (!transferCurrency.equals(targetBankAccount.defaultCurrency())) {
      throw new InvalidInputDataException(
          "Impossible to transfer with this currency. Incompatible Target account");
    }

    try {

      sourceBankAccount.withDrawMoney(transferAmount, transferCurrency,
          transferConcept, BankAccountId.fromId(targetAccountId));
      targetBankAccount.depositMoney(transferAmount, transferCurrency,
          transferConcept, BankAccountId.fromId(sourceAccountId));

      this.bankAccountRepository.update(sourceBankAccount);
      this.bankAccountRepository.update(targetBankAccount);

    } catch (DomainValidationException dvEx) {
      log.error("Invalid input parameters to transfer money", dvEx);
      throw new InvalidInputDataException(dvEx.getMessage());
    }
  }

}
