package com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model;

public enum ErrorCodeEnum {

  VALIDATION_DATA_ERROR("err-01", "Invalid input data"),
  DATABASE_OPERATION_ERROR("err-02", "Internal error");

  private final String errorCode;
  private final String errorMessage;

  ErrorCodeEnum(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
