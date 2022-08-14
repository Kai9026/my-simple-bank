package com.github.kai9026.mysimplebank.domain.bankaccount;

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
  private static final String DEFAULT_CURRENCY = "EUR";
  private static final Double DEFAULT_AMOUNT = 0.00;

  private static final int MONTHS_PERIOD_DURATION = 6;

  private final BankAccountNumber bankAccountNumber;
  private Money balanceTotalAccount;
  private String aliasAccount;

  private String defaultCurrency;
  private final CustomerId customerCode;
  private final LocalDate startIntervalDate;
  private final LocalDate closeIntervalDate;
  private List<BankAccountTransaction> transactionsInInterval;

  private BankAccount(final BankAccountId bankAccountId, final String alias,
      final Money balance, final String currency, final CustomerId customerCode,
      final LocalDate startIntervalDate) {
    super(bankAccountId);
    this.aliasAccount = alias;
    this.customerCode = customerCode;
    this.balanceTotalAccount = balance;
    this.defaultCurrency = currency;
    this.bankAccountNumber = BankAccountNumber.generate();
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
    final var balance = Money.of(DEFAULT_AMOUNT, currency);
    return new BankAccount(bankAccountId, alias, balance, currency, customerId, openIntervalDate);
  }

  public static BankAccount initAccountWithTransactions(final String alias,
      final String currency, final UUID accountId, final UUID customerCode,
      final LocalDate openIntervalDate, final List<BankAccountTransaction> transactions) {

    final var bankAccount = initAccountWith(alias, currency, accountId, customerCode,
        openIntervalDate);
    bankAccount.addTransactions(transactions);
    bankAccount.calculateBalance();
    return bankAccount;
  }

  public BankAccountNumber accountNumber() {
    return this.bankAccountNumber;
  }

  public Money accountBalance() {
    return this.balanceTotalAccount;
  }

  public String aliasAccount() {
    return this.aliasAccount;
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

  private void addTransactions(final List<BankAccountTransaction> transactions) {
    transactionsInInterval.addAll(transactions);
  }

  public void changeAliasAccount(final String newAliasAccount) {
    this.aliasAccount = newAliasAccount;
  }

  public void depositMoney(final double moneyAmount, final String currency,
      final String concept, final UUID originAccountId) {
    if (isEmptyField(currency) || !currency.equals(this.defaultCurrency)) {
      throw new DomainValidationException(
          "Currency is incorrect. Must be non empty and '" + this.defaultCurrency + "'");
    }
    final var originBankAccountId = BankAccountId.fromId(originAccountId);
    final var transaction =
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(), originBankAccountId,
            this.id(),
            Money.of(moneyAmount, currency), concept);
    this.transactionsInInterval.add(transaction);
    this.calculateBalance();
  }

  public void calculateBalance() {
    final var positiveBalance = this.transactionsInInterval.stream()
        .filter(tx -> tx.targetAccountCode().equals(this.id()))
        .map(BankAccountTransaction::transactionAmount)
        .reduce(Money::sum).orElse(Money.of(DEFAULT_AMOUNT, DEFAULT_CURRENCY));

    final var negativeBalance = this.transactionsInInterval.stream()
        .filter(tx -> tx.originAccountCode().equals(this.id()) &&
            !tx.targetAccountCode().equals(this.id()))
        .map(BankAccountTransaction::transactionAmount)
        .reduce(Money::sum).orElse(Money.of(DEFAULT_AMOUNT, DEFAULT_CURRENCY));

    final var totalMoney = positiveBalance.subtract(negativeBalance);
    if (!totalMoney.isPositiveOrZero()) {
      throw new DomainValidationException("Balance account cannot be lower than zero");
    }
    this.balanceTotalAccount = totalMoney;
  }
}
