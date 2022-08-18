package com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.service;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.BankAccountCreationUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.mapper.BankAccountCreationUseCaseMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.creation.model.BankAccountCreationRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class BankAccountCreationService implements BankAccountCreationUseCase {

  private final BankAccountRepository bankAccountRepository;
  private final BankAccountCreationUseCaseMapper bankAccountCreationUseCaseMapper;

  @Override
  @Transactional
  public BankAccountBaseResponse createNewBankAccount(BankAccountCreationRequest request) {

    try {

      final var newBankAccount =
          this.bankAccountCreationUseCaseMapper.toBankAccount(request);

      return this.bankAccountCreationUseCaseMapper
          .toBankAccountBaseResponse(this.bankAccountRepository.save(newBankAccount));

    } catch (DomainValidationException dvEx) {
      log.error("Invalid input parameters to create new bank account", dvEx);
      throw new InvalidInputDataException(dvEx.getMessage());
    }
  }
}
