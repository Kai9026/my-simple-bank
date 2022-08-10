package com.github.kai9026.mysimplebank.application.exception;

public class DuplicateCustomerException extends RuntimeException {

  private static final long serialVersionUID = -6144234947309809139L;
  private static final String DEFAULT_MESSAGE = "Customer with email '%s' already exists";

  public DuplicateCustomerException(final String email) {
    super(String.format(DEFAULT_MESSAGE, email));
  }

}
