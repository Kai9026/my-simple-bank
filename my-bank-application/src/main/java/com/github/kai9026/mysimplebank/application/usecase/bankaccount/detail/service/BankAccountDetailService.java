package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.service;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.BankAccountDetailUseCase;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.mapper.BankAccountDetailMapper;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailRequest;
import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.BankAccountDetailResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.repository.BankAccountRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BankAccountDetailService implements BankAccountDetailUseCase {

  private static final String FROM_DATE = "from";
  private static final String TO_DATE = "to";
  private final BankAccountRepository bankAccountRepository;
  private final BankAccountDetailMapper bankAccountDetailMapper;

  @Override
  public BankAccountDetailResponse getBankAccountDetail(BankAccountDetailRequest request) {

    final var transactionsFrom = request.transactionsFrom();
    final var transactionsTo = request.transactionsTo();
    final var bankAccountCode = request.bankAccountCode();

    final var bankAccount = bankAccountRepository.findById(BankAccountId.fromId(bankAccountCode))
        .orElseThrow(() -> new InvalidInputDataException("Bank account invalid"));

    final var searchInterval =
        this.calculateSearchInterval(transactionsFrom, transactionsTo, bankAccount);

    final var transactionsList =
        this.filterTransactionsInInterval(
            searchInterval.get(FROM_DATE), searchInterval.get(TO_DATE), bankAccount);

    return this.bankAccountDetailMapper.toBankAccountDetailResponse(
        bankAccount.accountBalance().amount().floatValue(),
        transactionsList);
  }

  private List<BankAccountTransaction> filterTransactionsInInterval(
      final LocalDate transactionsFrom,
      final LocalDate transactionsTo, final BankAccount bankAccount) {
    return bankAccount.activeTransactions()
        .stream()
        .filter(tx -> tx.transactionDate().isAfter(transactionsFrom) &&
            tx.transactionDate().isBefore(transactionsTo)).toList();
  }

  private Map<String, LocalDate> calculateSearchInterval(LocalDate transactionsFrom,
      LocalDate transactionsTo, BankAccount bankAccount) {
    var intervalSearchMap = new HashMap<String, LocalDate>();
    intervalSearchMap.put(FROM_DATE, transactionsFrom);
    intervalSearchMap.put(TO_DATE, transactionsTo);

    if (transactionsTo.isBefore(transactionsFrom)) {
      throw new InvalidInputDataException("Invalid interval query dates");
    }
    if (transactionsFrom.isBefore(bankAccount.openIntervalDate()) &&
        transactionsTo.isBefore(bankAccount.openIntervalDate())) {
      throw new InvalidInputDataException(
          "Cannot retrieve transactions in interval. Please, contact with your bank");
    }
    if (transactionsFrom.isBefore(bankAccount.openIntervalDate()) &&
        transactionsTo.isAfter(bankAccount.openIntervalDate())) {
      intervalSearchMap.put(FROM_DATE, bankAccount.openIntervalDate());
    }
    if (transactionsFrom.isAfter(bankAccount.openIntervalDate()) &&
        transactionsTo.isAfter(bankAccount.closeIntervalDate())) {
      intervalSearchMap.put(TO_DATE, bankAccount.closeIntervalDate());
    }

    return intervalSearchMap;
  }
}
