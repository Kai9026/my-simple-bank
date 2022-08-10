package com.github.kai9026.mysimplebank.application.exception;

public class InvalidInputDataException extends RuntimeException {

  private static final long serialVersionUID = -6144234947309809139L;

  public InvalidInputDataException(String exceptionDetail) {
    super(exceptionDetail);
  }
}
