package com.github.kai9026.mysimplebank.domain.customer.id;

import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.domain.shared.objects.DomainObjectId;
import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;
import java.util.UUID;

public class CustomerId extends DomainObjectId<UUID> implements ValueObject {

  public CustomerId(UUID id) {
    super(id);
  }

  public static CustomerId fromId(final UUID identity) {
    if (isNull(identity)) {
      throw new IllegalArgumentException("CustomerId cannot be null");
    }

    return new CustomerId(identity);
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
    final var otherCustomerId = (CustomerId) o;
    return Objects.equals(id, otherCustomerId.id);
  }

  @Override
  public String toString() {
    return "CustomerId{id=" + id + '}';
  }
}
