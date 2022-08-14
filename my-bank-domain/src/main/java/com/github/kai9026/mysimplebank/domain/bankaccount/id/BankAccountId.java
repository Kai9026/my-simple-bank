package com.github.kai9026.mysimplebank.domain.bankaccount.id;

import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.domain.shared.objects.DomainObjectId;
import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;
import java.util.UUID;

public class BankAccountId extends DomainObjectId<UUID> implements ValueObject {

  private BankAccountId(UUID id) {
    super(id);
  }

  public static BankAccountId fromId(final UUID identity) {
    if (isNull(identity)) {
      throw new IllegalArgumentException("BankAccountId cannot be null");
    }

    return new BankAccountId(identity);
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
    final var otherAccountId = (BankAccountId) o;
    return Objects.equals(id, otherAccountId.id);
  }

  @Override
  public String toString() {
    return "AccountId{id=" + id + '}';
  }
}
