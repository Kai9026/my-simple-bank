package com.github.kai9026.mysimplebank.domain.customer;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import com.github.kai9026.mysimplebank.domain.shared.objects.ValueObject;
import java.util.Objects;

public class PostalAddress implements ValueObject {

  private static final long serialVersionUID = -8154021303114421749L;

  private final String streetAddress;
  private final Integer postalCode;
  private final String city;

  private PostalAddress(final String streetAddress, final Integer postalCode, final String city) {
    this.streetAddress = streetAddress;
    this.postalCode = postalCode;
    this.city = city;
  }

  public static PostalAddress of(final String streetAddress, final Integer postalCode,
      final String city) {
    PostalAddress.validatePostalAddressFields(streetAddress, postalCode, city);

    return new PostalAddress(streetAddress, postalCode, city);
  }

  private static void validatePostalAddressFields(String streetAddress, Integer postalCode,
      String city) {
    if (isEmptyField(streetAddress) || isNull(postalCode) || isEmptyField(city)) {
      throw new DomainValidationException("Street address, postal code and city cannot be null");
    } else if (!(postalCode > 0 && postalCode < 99999)) {
      throw new DomainValidationException("Postal code format is not valid");
    }
  }

  public String streetAddress() {
    return this.streetAddress;
  }

  public Integer postalCode() {
    return this.postalCode;
  }

  public String city() {
    return this.city;
  }

  @Override
  public int hashCode() {
    return hash(this.streetAddress, this.postalCode, this.city);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final var otherPostalAddress = (PostalAddress) o;
    return Objects.equals(this.streetAddress, otherPostalAddress.streetAddress) &&
        Objects.equals(this.postalCode, otherPostalAddress.postalCode) &&
        Objects.equals(this.city, otherPostalAddress.city);
  }

  @Override
  public String toString() {
    return "Postal address='" + this.streetAddress + ", " + this.postalCode + ", " + this.city
        + "'";
  }
}
