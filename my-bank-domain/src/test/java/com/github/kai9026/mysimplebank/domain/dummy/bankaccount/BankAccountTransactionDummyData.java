package com.github.kai9026.mysimplebank.domain.dummy.bankaccount;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.Money;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import java.time.LocalDate;
import java.util.UUID;

public class BankAccountTransactionDummyData {

  public static BankAccountTransaction createBankAccountTransaction(DummyData dummyData) {
    return BankAccountTransaction.createTransactionWith(dummyData.transactionAccountId,
        dummyData.originBankAccountId, dummyData.targetBankAccountId, dummyData.transactionAmount,
        dummyData.transactionConcept);
  }


  public enum DummyData {
    VALID_DATA(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
        Money.of(12.00, "EUR"), "Mall", LocalDate.now()),
    OTHER_VALID_DATA(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
        Money.of(14.00, "EUR"), "Mall 2", LocalDate.now()),
    INVALID_DATA_NULL_AMOUNT(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null,
        "Mall 2", LocalDate.now()),
    INVALID_DATA_NULL_CONCEPT(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
        Money.of(14.00, "EUR"), null, LocalDate.now());

    private final UUID transactionAccountId;
    private final BankAccountId originBankAccountId;
    private final BankAccountId targetBankAccountId;
    private final Money transactionAmount;
    private final String transactionConcept;
    private final LocalDate transactionDate;

    DummyData(UUID transactionAccountId, UUID originBankAccountId, UUID targetBankAccountId,
        Money transactionAmount, String transactionConcept, LocalDate transactionDate) {
      this.transactionAccountId = transactionAccountId;
      this.originBankAccountId = BankAccountId.fromId(originBankAccountId);
      this.targetBankAccountId = BankAccountId.fromId(targetBankAccountId);
      this.transactionAmount = transactionAmount;
      this.transactionConcept = transactionConcept;
      this.transactionDate = transactionDate;
    }

    public UUID getTransactionAccountId() {
      return transactionAccountId;
    }

    public BankAccountId getOriginBankAccountId() {
      return originBankAccountId;
    }

    public BankAccountId getTargetBankAccountId() {
      return targetBankAccountId;
    }

    public Money getTransactionAmount() {
      return transactionAmount;
    }

    public String getTransactionConcept() {
      return transactionConcept;
    }

    public LocalDate getTransactionDate() {
      return transactionDate;
    }
  }
}
