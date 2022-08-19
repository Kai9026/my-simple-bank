package com.github.kai9026.mysimplebank.domain.bankaccount;

import static com.github.kai9026.mysimplebank.domain.bankaccount.Money.CURRENCY_EUR_MONEY;
import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import com.github.kai9026.mysimplebank.domain.shared.objects.AggregateRoot;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount extends AggregateRoot<BankAccountId> {

  private static final long serialVersionUID = -4350072396686438571L;
  private static final int MONTHS_PERIOD_DURATION = 6;

  private final BankAccountNumber bankAccountNumber;
  private Money consolidatedBalance;
  private Money intervalBalance;
  private String accountAlias;
  private final String defaultCurrency;
  private final CustomerId customerCode;
  private final LocalDate startIntervalDate;
  private final LocalDate closeIntervalDate;
  private List<BankAccountTransaction> transactionsInInterval;

  private BankAccount(final BankAccountId bankAccountId, final String alias,
      final BankAccountNumber accountNumber, final Money balance, final String currency,
      final CustomerId customerCode, final LocalDate startIntervalDate) {
    super(bankAccountId);
    this.accountAlias = alias;
    this.customerCode = customerCode;
    this.intervalBalance = balance;
    this.consolidatedBalance = Money.of(Money.ZERO_MONEY, currency);
    this.defaultCurrency = currency;
    this.bankAccountNumber = accountNumber;
    this.startIntervalDate = startIntervalDate == null ? LocalDate.now() : startIntervalDate;
    this.closeIntervalDate = this.startIntervalDate.plusMonths(MONTHS_PERIOD_DURATION);
    this.transactionsInInterval = new ArrayList<>();
  }

  public static BankAccount initAccountWith(final String alias, final String currency,
      final UUID accountId, final UUID customerCode, final LocalDate openIntervalDate) {
    if (isEmptyField(currency)) {
      throw new DomainValidationException("Currency cannot be null");
    }
    if (isNull(customerCode)) {
      throw new DomainValidationException("Customer code is required");
    }

    final var bankAccountId = BankAccountId.fromId(accountId);
    final var customerId = CustomerId.fromId(customerCode);
    final var balance = Money.of(Money.ZERO_MONEY, currency);
    final var accountNumber = BankAccountNumber.generate();
    return new BankAccount(bankAccountId, alias, accountNumber, balance, currency, customerId,
        openIntervalDate);
  }

  public static BankAccount initAccountWithTransactions(final String alias,
      final String accountNumber, final String currency, final UUID accountId,
      final UUID customerCode,
      final LocalDate openIntervalDate, final double consolidatedBalance,
      final List<BankAccountTransaction> transactions) {
    if (isEmptyField(currency)) {
      throw new DomainValidationException("Currency cannot be null");
    }
    if (isNull(customerCode)) {
      throw new DomainValidationException("Customer code is required");
    }

    final var bankAccountId = BankAccountId.fromId(accountId);
    final var bankAccountNumber = BankAccountNumber.fromString(accountNumber);
    final var customerId = CustomerId.fromId(customerCode);
    final var bankAccount = new BankAccount(bankAccountId, alias, bankAccountNumber,
        Money.of(Money.ZERO_MONEY, currency),
        currency, customerId, openIntervalDate);
    bankAccount.updateConsolidatedBalanceTo(consolidatedBalance);
    bankAccount.addTransactions(transactions);
    bankAccount.calculateBalance();
    return bankAccount;
  }

  public BankAccountNumber accountNumber() {
    return this.bankAccountNumber;
  }

  public Money accountBalance() {
    return this.consolidatedBalance.sum(this.intervalBalance);
  }

  public String aliasAccount() {
    return this.accountAlias;
  }

  public CustomerId accountCustomer() {
    return this.customerCode;
  }

  public LocalDate openIntervalDate() {
    return this.startIntervalDate;
  }

  public LocalDate closeIntervalDate() {
    return this.closeIntervalDate;
  }

  public List<BankAccountTransaction> activeTransactions() {
    return this.transactionsInInterval;
  }

  public String defaultCurrency() {
    return this.defaultCurrency;
  }

  public Money intervalBalance() {
    return this.intervalBalance;
  }

  public Money consolidatedBalance() {
    return this.consolidatedBalance;
  }

  private void addTransactions(final List<BankAccountTransaction> transactions) {
    transactionsInInterval.addAll(transactions);
  }

  public void changeAliasAccount(final String newAliasAccount) {
    this.accountAlias = newAliasAccount;
  }

  private void updateConsolidatedBalanceTo(final double amount) {
    this.consolidatedBalance = Money.of(amount, this.defaultCurrency);
  }

  public void closeCurrentInterval() {
    this.consolidatedBalance = this.consolidatedBalance.sum(this.intervalBalance);
    this.intervalBalance = Money.of(Money.ZERO_MONEY, this.defaultCurrency);
  }

  public void depositMoney(final double amountToDeposit, final String currency,
      final String concept, final BankAccountId originAccountId) {
    if (isEmptyField(currency) || !currency.equals(this.defaultCurrency)) {
      throw new DomainValidationException(
          "Currency is incorrect. Must be non empty and '" + this.defaultCurrency + "'");
    }
    final var transaction =
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(), originAccountId,
            this.id(), Money.of(amountToDeposit, currency), concept, true);
    this.transactionsInInterval.add(transaction);
    this.calculateBalance();
  }

  public void withDrawMoney(final double amountToWithdraw, final String currency,
      final String concept, final BankAccountId targetAccountId) {
    if (isEmptyField(currency) || !currency.equals(this.defaultCurrency)) {
      throw new DomainValidationException(
          "Currency is incorrect. Must be non empty and '" + this.defaultCurrency + "'");
    }
    final var moneyToWithdraw = Money.of(amountToWithdraw, currency);
    if (!accountBalance().subtract(moneyToWithdraw).isPositiveOrZero()) {
      throw new DomainValidationException("Impossible to perform the operation. Not enough money");
    }
    final var transaction =
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(), this.id(),
            targetAccountId, Money.of(amountToWithdraw, currency), concept, true);
    this.transactionsInInterval.add(transaction);
    this.calculateBalance();
  }

  public void calculateBalance() {
    final var positiveBalance = this.transactionsInInterval.stream()
        .filter(tx -> tx.targetAccountCode().equals(this.id()))
        .map(BankAccountTransaction::transactionAmount)
        .reduce(Money::sum).orElse(Money.of(Money.ZERO_MONEY, CURRENCY_EUR_MONEY));

    final var negativeBalance = this.transactionsInInterval.stream()
        .filter(tx -> tx.originAccountCode().equals(this.id()) &&
            !tx.targetAccountCode().equals(this.id()))
        .map(BankAccountTransaction::transactionAmount)
        .reduce(Money::sum).orElse(Money.of(Money.ZERO_MONEY, CURRENCY_EUR_MONEY));

    this.intervalBalance = positiveBalance.subtract(negativeBalance);

    final var totalMoney = consolidatedBalance().sum(intervalBalance);
    if (!totalMoney.isPositiveOrZero()) {
      throw new DomainValidationException("Balance account cannot be lower than zero");
    }
  }
}
