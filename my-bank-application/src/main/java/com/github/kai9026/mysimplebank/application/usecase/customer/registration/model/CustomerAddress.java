package com.github.kai9026.mysimplebank.application.usecase.customer.registration.model;

public class CustomerAddress {

  private String street;
  private Integer postalCode;
  private String city;

  public CustomerAddress(String street, Integer postalCode, String city) {
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(Integer postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "CustomerAddress{" +
        "street='" + street + '\'' +
        ", postalCode=" + postalCode +
        ", city='" + city + '\'' +
        '}';
  }
}
