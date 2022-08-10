package com.github.kai9026.mysimplebank.domain.exception;

public class DomainValidationException extends RuntimeException {

  private static final long serialVersionUID = -4177265420170414039L;

  public DomainValidationException(String exceptionDetail) {
    super(exceptionDetail);
  }
}
