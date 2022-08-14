package com.github.kai9026.mysimplebank.domain.dummy.customer;

import com.github.kai9026.mysimplebank.domain.customer.PostalAddress;

public class PostalAddressDummyData {

  public static PostalAddress createPostalAddressDummy(DummyData dummyData) {
    return PostalAddress.of(dummyData.streetAddress, dummyData.postalCode, dummyData.city);
  }

  public enum DummyData {
    VALID_DATA("Test street", 37001, "Salamanca"),
    OTHER_VALID_DATA("Test street 2", 28035, "Madrid"),
    INVALID_DATA_NULL_STREET_ADDRESS(null, 37001, "Salamanca"),
    INVALID_DATA_NULL_POSTAL_CODE("Test street", null, "Salamanca"),
    INVALID_DATA_INVALID_POSTAL_CODE("Test street", 0, "Salamanca"),
    INVALID_DATA_NULL_CITY("Test street", 37001, null);

    private final String streetAddress;
    private Integer postalCode;
    private String city;

    DummyData(final String streetAddress, final Integer postalCode, final String city) {
      this.streetAddress = streetAddress;
      this.postalCode = postalCode;
      this.city = city;
    }

    public String getStreetAddress() {
      return streetAddress;
    }

    public Integer getPostalCode() {
      return postalCode;
    }

    public String getCity() {
      return city;
    }
  }
}
