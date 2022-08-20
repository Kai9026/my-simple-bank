package com.github.kai9026.mysimplebank.domain.dummy.bankaccount;

import static com.github.kai9026.mysimplebank.domain.dummy.bankaccount.BankAccountDummyData.DummyData.VALID_DATA;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.Money;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BankAccountDummyData {

  private static final String ACCOUNT_NUMBER = "3214 4245 8473 3213";

  public static BankAccount createBankAccount(DummyData dummyData) {
    return BankAccount.initAccountWith(dummyData.aliasAccount, dummyData.defaultCurrency,
        dummyData.bankAccountId, dummyData.customerCode, dummyData.startIntervalDate);
  }

  public static BankAccount createBankAccountWithThreeValidTransactions() {
    final var accountId = UUID.randomUUID();
    List<BankAccountTransaction> transactions = List.of(
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(accountId), BankAccountId.fromId(UUID.randomUUID()),
            Money.of(10.00, "EUR"), "Bizum", null, true),
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(accountId), BankAccountId.fromId(accountId),
            Money.of(15.00, "EUR"), "Deposit", null, true),
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(UUID.randomUUID()), BankAccountId.fromId(accountId),
            Money.of(100.00, "EUR"), "PC", null, true)
    );

    return BankAccount.initAccountWithTransactions(VALID_DATA.aliasAccount,
        VALID_DATA.getBankAccountNumber(), VALID_DATA.defaultCurrency,
        accountId, VALID_DATA.customerCode, VALID_DATA.startIntervalDate.minusDays(1),
        VALID_DATA.getConsolidatedBalance(), transactions);
  }


  public enum DummyData {
    VALID_DATA(UUID.randomUUID(), "Save account", ACCOUNT_NUMBER, UUID.randomUUID(), "EUR",
        LocalDate.now(), 0.00),
    OTHER_VALID_DATA(UUID.randomUUID(), "Invest account", ACCOUNT_NUMBER, UUID.randomUUID(), "EUR",
        LocalDate.now(), 10.00),
    INVALID_DATA_NULL_CURRENCY(UUID.randomUUID(), "Invest account", ACCOUNT_NUMBER,
        UUID.randomUUID(), null, LocalDate.now(), 0.00),
    INVALID_DATA_NULL_CUSTOMER(UUID.randomUUID(), "Invest account", ACCOUNT_NUMBER, null, "EUR",
        LocalDate.now(), 0.00);

    private final UUID bankAccountId;
    private final String bankAccountNumber;
    private final String aliasAccount;
    private final String defaultCurrency;
    private final UUID customerCode;
    private final LocalDate startIntervalDate;
    private final double consolidatedBalance;

    DummyData(final UUID bankAccountId, final String aliasAccount, final String accountNumber,
        final UUID customerCode, final String defaultCurrency, final LocalDate startIntervalDate,
        final double consolidatedBalance) {
      this.bankAccountId = bankAccountId;
      this.aliasAccount = aliasAccount;
      this.bankAccountNumber = accountNumber;
      this.customerCode = customerCode;
      this.defaultCurrency = defaultCurrency;
      this.startIntervalDate = startIntervalDate;
      this.consolidatedBalance = consolidatedBalance;
    }

    public UUID getBankAccountId() {
      return bankAccountId;
    }

    public String getAliasAccount() {
      return aliasAccount;
    }

    public String getBankAccountNumber() {
      return bankAccountNumber;
    }

    public UUID getCustomerCode() {
      return customerCode;
    }

    public String getDefaultCurrency() {
      return defaultCurrency;
    }

    public LocalDate getStartIntervalDate() {
      return startIntervalDate;
    }

    public double getConsolidatedBalance() {
      return consolidatedBalance;
    }
  }
}
