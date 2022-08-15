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

  public static BankAccount createBankAccount(DummyData dummyData) {
    return BankAccount.initAccountWith(dummyData.aliasAccount, dummyData.defaultCurrency,
        dummyData.bankAccountId, dummyData.customerCode, dummyData.startIntervalDate);
  }

  public static BankAccount createBankAccountWithValidTransactions() {
    final var accountId = UUID.randomUUID();
    List<BankAccountTransaction> transactions = List.of(
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(accountId), BankAccountId.fromId(UUID.randomUUID()),
            Money.of(10.00, "EUR"), "Bizum"),
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(accountId), BankAccountId.fromId(accountId),
            Money.of(15.00, "EUR"), "Deposit"),
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(UUID.randomUUID()), BankAccountId.fromId(accountId),
            Money.of(100.00, "EUR"), "PC")
    );

    return BankAccount.initAccountWithTransactions(VALID_DATA.aliasAccount, VALID_DATA.defaultCurrency,
        accountId, VALID_DATA.customerCode, VALID_DATA.startIntervalDate.minusDays(1),
        transactions);
  }


  public enum DummyData {
    VALID_DATA(UUID.randomUUID(), "Save account", UUID.randomUUID(), "EUR",
        LocalDate.now()),
    OTHER_VALID_DATA(UUID.randomUUID(), "Invest account", UUID.randomUUID(), "EUR",
        LocalDate.now()),
    INVALID_DATA_NULL_CURRENCY(UUID.randomUUID(), "Invest account", UUID.randomUUID(), null,
        LocalDate.now()),
    INVALID_DATA_NULL_CUSTOMER(UUID.randomUUID(), "Invest account",null, "EUR",
        LocalDate.now());

    private final UUID bankAccountId;
    private final String aliasAccount;
    private final String defaultCurrency;
    private final UUID customerCode;
    private final LocalDate startIntervalDate;

    DummyData(final UUID bankAccountId, final String aliasAccount,
        final UUID customerCode, final String defaultCurrency, final LocalDate startIntervalDate) {
      this.bankAccountId = bankAccountId;
      this.aliasAccount = aliasAccount;
      this.customerCode = customerCode;
      this.defaultCurrency = defaultCurrency;
      this.startIntervalDate = startIntervalDate;
    }

    public UUID getBankAccountId() {
      return bankAccountId;
    }

    public String getAliasAccount() {
      return aliasAccount;
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

  }
}
