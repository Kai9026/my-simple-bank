package com.github.kai9026.mysimplebank.domain.dummy.bankaccount.id;

import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountTransactionId;
import java.util.UUID;

public class BankAccountTransactionIdDummyData {

  private static final UUID UUID_VALUE = UUID.randomUUID();

  public static BankAccountTransactionId createBankAccountTransactionId(DummyData dummyData) {
    return BankAccountTransactionId.fromId(dummyData.id);
  }

  public enum DummyData {

    VALID_DATA(UUID_VALUE),
    OTHER_VALID_DATA(UUID.randomUUID()),
    INVALID_DATA_NULL_UUID(null);

    private final UUID id;

    DummyData(final UUID id) {
      this.id = id;
    }

    public UUID getId() {
      return id;
    }

  }
}
