package com.github.kai9026.mysimplebank.infrastructure.web.errorhandling;

import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorModel;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler({InvalidInputDataException.class, DuplicateCustomerException.class})
  public ResponseEntity<ErrorModel> handleInvalidInputData(RuntimeException ex) {
    final var errorCode = ErrorCodeEnum.VALIDATION_DATA_ERROR;
    final var responseErrorModel =
        new ErrorModel(errorCode.getErrorCode(), errorCode.getErrorMessage(), ex.getMessage());

    return ResponseEntity.badRequest().body(responseErrorModel);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorModel> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    final var errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(","));
    final var errorCode = ErrorCodeEnum.VALIDATION_DATA_ERROR;
    final var responseErrorModel =
        new ErrorModel(errorCode.getErrorCode(), errorCode.getErrorMessage(), errors);

    return ResponseEntity.badRequest().body(responseErrorModel);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorModel> handleDataAccess(DataAccessException ex) {
    final var errorCode = ErrorCodeEnum.DATABASE_OPERATION_ERROR;
    final var responseErrorModel =
        new ErrorModel(errorCode.getErrorCode(), errorCode.getErrorMessage(), ex.getMessage());

    return ResponseEntity.internalServerError().body(responseErrorModel);
  }

}
