package com.github.kai9026.mysimplebank.domain.bankaccount.id;

import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.domain.shared.objects.DomainObjectId;
import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;
import java.util.UUID;

public class BankAccountTransactionId extends DomainObjectId<UUID> implements ValueObject {

  private BankAccountTransactionId(UUID id) {
    super(id);
  }

  public static BankAccountTransactionId fromId(final UUID identity) {
    if (isNull(identity)) {
      throw new IllegalArgumentException("BankAccountTransactionId cannot be null");
    }

    return new BankAccountTransactionId(identity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherBankAccountTransactionId = (BankAccountTransactionId) o;
    return Objects.equals(id, otherBankAccountTransactionId.id);
  }

  @Override
  public String toString() {
    return "BankAccountTransactionId{id=" + id + '}';
  }


}
