package com.github.kai9026.mysimplebank.domain.dummy.customer.id;

import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import java.util.UUID;

public class CustomerIdDummyData {

  private static final UUID UUID_VALUE = UUID.randomUUID();

  public static CustomerId createCustomerId(DummyData dummyData) {
    return CustomerId.fromId(dummyData.id);
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
