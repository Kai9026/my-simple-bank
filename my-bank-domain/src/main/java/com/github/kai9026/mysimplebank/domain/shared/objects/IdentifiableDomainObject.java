package com.github.kai9026.mysimplebank.domain.shared.objects;

import java.io.Serializable;

public interface IdentifiableDomainObject<I extends Serializable> extends DomainObject {

  I id();
}