package com.github.kai9026.mysimplebank.domain.shared.objects;

import java.util.Objects;

public abstract class Entity<I extends DomainObjectId> implements IdentifiableDomainObject<I> {

  private final I id;

  protected Entity(I id) {
    this.id = Objects.requireNonNull(id, "id must not be null");
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
    Entity<?> entity = (Entity<?>) o;
    return Objects.equals(this.id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return String.format("%s[%s]", getClass().getSimpleName(), this.id);
  }
}