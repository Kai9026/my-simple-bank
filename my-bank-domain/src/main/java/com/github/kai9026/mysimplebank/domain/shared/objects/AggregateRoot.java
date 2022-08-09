package com.github.kai9026.mysimplebank.domain.shared.objects;

public abstract class AggregateRoot<I extends DomainObjectId> extends Entity<I> {

  protected AggregateRoot(I id) {
    super(id);
  }

}