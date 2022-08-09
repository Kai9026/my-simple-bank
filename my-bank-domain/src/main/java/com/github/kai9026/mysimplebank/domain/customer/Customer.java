package com.github.kai9026.mysimplebank.domain.customer;

import com.github.kai9026.mysimplebank.domain.customer.id.CustomerId;
import com.github.kai9026.mysimplebank.domain.shared.objects.AggregateRoot;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
import java.util.regex.Pattern;

public class Customer extends AggregateRoot<CustomerId> {

  private static final long serialVersionUID = -4753588888777325217L;
  private static final int MIN_CUSTOMER_AGE = 18;
  private static final String PWD_REQUIRED_FORMAT =
      "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";

  private final CustomerName customerFullName;
  private PostalAddress customerAddress;
  private EmailAddress emailAddress;
  private final LocalDate birthDate;
  private final String nickName;
  private Password password;

  private Customer(Builder builder) {
    super(builder.customerId);
    this.customerFullName = builder.fullName;
    this.customerAddress = builder.streetAddress;
    this.emailAddress = builder.email;
    this.birthDate = builder.birthDate;
    this.nickName = builder.nickName;
    this.password = builder.password;
  }

  public void changeEmail(final String email) {
    this.emailAddress = EmailAddress.of(email);
  }

  public void changeAddress(final String streetAddress, final int postalCode, final String city) {
    this.customerAddress = PostalAddress.of(streetAddress, postalCode, city);
  }

  public void changePassword(final String pwd) {
    Customer.validatePasswordContent(pwd);
    this.password = Password.of(pwd);
  }

  public CustomerName customerFullName() {
    return customerFullName;
  }

  public PostalAddress customerAddress() {
    return customerAddress;
  }

  public EmailAddress emailAddress() {
    return emailAddress;
  }

  public LocalDate birthDate() {
    return birthDate;
  }

  public String nickName() {
    return nickName;
  }

  public Password password() {
    return password;
  }

  // Builder pattern
  public interface IdStep {
    FullNameStep id(UUID customerId);
  }

  public interface FullNameStep {
    StreetAddressStep fullName(String firstName, String lastName);
  }

  public interface StreetAddressStep {
    EmailAddressStep streetAddress(String streetAddress, Integer postalCode, String city);
  }

  public interface EmailAddressStep {
    BirthDateStep emailAddress(String email);
  }

  public interface BirthDateStep {
    NickNameStep birthDate(LocalDate birthDate);
  }

  public interface NickNameStep {
    PasswordStep nickName(String nickName);
  }

  public interface PasswordStep {
    Build password(String password);
  }

  public interface Build {
    Customer build();
  }

  public static class Builder implements IdStep, FullNameStep, StreetAddressStep, EmailAddressStep,
      BirthDateStep, NickNameStep, PasswordStep, Build {

    private CustomerId customerId;
    private CustomerName fullName;
    private PostalAddress streetAddress;
    private EmailAddress email;
    private LocalDate birthDate;
    private String nickName;
    private Password password;

    @Override
    public FullNameStep id(UUID customerId) {
      this.customerId = CustomerId.fromId(customerId);
      return this;
    }

    @Override
    public StreetAddressStep fullName(String firstName, String lastName) {
      this.fullName = CustomerName.of(firstName, lastName);
      return this;
    }

    @Override
    public EmailAddressStep streetAddress(String streetAddress, Integer postalCode, String city) {
      this.streetAddress = PostalAddress.of(streetAddress, postalCode, city);
      return this;
    }

    @Override
    public BirthDateStep emailAddress(String email) {
      this.email = EmailAddress.of(email);
      return this;
    }

    @Override
    public NickNameStep birthDate(LocalDate birthDate) {
      Customer.validateCustomerAge(birthDate);

      this.birthDate = birthDate;
      return this;
    }

    @Override
    public PasswordStep nickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    @Override
    public Build password(String password) {
      Customer.validatePasswordContent(password);

      this.password = Password.of(password);
      return this;
    }

    @Override
    public Customer build() {
      return new Customer(this);
    }
  }

  // Enforcing business invariants
  private static void validateCustomerAge(LocalDate birthDate) {
    final var now = LocalDate.now();
    final var yearsBetween = Period.between(birthDate, now).getYears();

    if (yearsBetween < MIN_CUSTOMER_AGE) {
      throw new IllegalArgumentException("Customer age must be greater than 18");
    }
  }

  private static void validatePasswordContent(String password) {
    final var regexPattern = Pattern.compile(PWD_REQUIRED_FORMAT);
    final var matcher = regexPattern.matcher(password);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Password format is not valid");
    }
  }

  @Override
  public String toString() {
    return "Customer{" +
        "fullName='" + this.customerFullName + '\'' +
        ", streetAddress='" + this.customerAddress + '\'' +
        ", email='" + this.emailAddress + '\'' +
        ", birthDate=" + this.birthDate +
        ", nickName='" + this.nickName + '\'' +
        ", password='" + this.password + '\'' +
        '}';
  }
}
