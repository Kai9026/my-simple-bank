package com.github.kai9026.mysimplebank.domain.dummy;

import com.github.kai9026.mysimplebank.domain.customer.Customer;
import java.time.LocalDate;
import java.util.UUID;

public class CustomerDummyData {

  public static Customer createCustomerAggregate(final DummyData dummyData) {
    return new Customer.Builder()
        .id(dummyData.id)
        .fullName(dummyData.getFirstName(), dummyData.getLastName())
        .streetAddress(dummyData.getStreetAddress(), dummyData.getPostalCode(), dummyData.getCity())
        .emailAddress(dummyData.getEmail())
        .birthDate(dummyData.getBirthDate())
        .password(dummyData.getPassword())
        .build();
  }

  public enum DummyData {
    VALID_DATA(UUID.randomUUID(), "Joe", "Cocker", "Street Joe", 103, "Detroit",
        "joe@gmx.com", LocalDate.of(1944, 5, 20),
        "Dt8c$1245as"),
    OTHER_VALID_DATA(UUID.randomUUID(), "Mick", "Lewis", "Street Mick", 101, "Detroit",
        "mick@gmx.com", LocalDate.of(1974, 5, 10),
        "D34c$1345."),
    INVALID_DATA_WRONG_BIRTHDATE(UUID.randomUUID(), "Joe", "Cocker", "Street Joe", 103, "Detroit",
        "joe@gmx.com", LocalDate.now(),
        "Dt8c$1245as"),
    INVALID_DATA_WRONG_PASSWORD(UUID.randomUUID(), "Joe", "Cocker", "Street Joe", 103, "Detroit",
        "joe@gmx.com", LocalDate.of(1944, 5, 20),
        "password");

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String streetAddress;
    private final Integer postalCode;
    private final String email;
    private final String city;
    private final LocalDate birthDate;
    private final String password;

    DummyData(final UUID id, final String firstName, final String lastName,
        final String streetAddress, final Integer postalCode,
        final String city, final String email, final LocalDate birthDate,
        final String password) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.streetAddress = streetAddress;
      this.postalCode = postalCode;
      this.city = city;
      this.email = email;
      this.birthDate = birthDate;
      this.password = password;
    }

    public UUID getId() {
      return id;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
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

    public String getEmail() {
      return email;
    }

    public LocalDate getBirthDate() {
      return birthDate;
    }

    public String getPassword() {
      return password;
    }
  }

}
