package com.github.kai9026.mysimplebank.domain.shared.objects;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

public class DomainObjectId<I extends Serializable> implements IdentifiableDomainObject<I> {

  private static final long serialVersionUID = 990416815252243045L;

  protected final I id;

  public DomainObjectId(I id) {
    this.id = requireNonNull(id, "Id cannot be null.");
  }

  @Override
  public I id() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomainObjectId<?> that = (DomainObjectId<?>) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "DomainObjectId{id=" + id + '}';
  }
}