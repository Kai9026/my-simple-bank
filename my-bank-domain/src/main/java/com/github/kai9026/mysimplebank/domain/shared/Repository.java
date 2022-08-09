package com.github.kai9026.mysimplebank.domain.shared;

import com.github.kai9026.mysimplebank.domain.shared.objects.AggregateRoot;
import com.github.kai9026.mysimplebank.domain.shared.objects.DomainObjectId;
import java.util.Optional;

public interface Repository<T extends AggregateRoot<?>, I extends DomainObjectId> {

  T save(T aggregate);

  Optional<T> findById(I id);

}