package com.github.kai9026.mysimplebank.infrastructure.web.errorhandling;

import static org.springframework.http.HttpStatus.*;

import com.github.kai9026.mysimplebank.application.exception.DuplicateCustomerException;
import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorModel;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler({InvalidInputDataException.class, DuplicateCustomerException.class})
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<ErrorModel> handleInvalidInputData(RuntimeException ex) {
    log.warn("[VALIDATION ERROR] - {}", ex.getMessage());
    final var errorCode = ErrorCodeEnum.VALIDATION_DATA_ERROR;
    final var responseErrorModel =
        new ErrorModel(errorCode.getErrorCode(), errorCode.getErrorMessage(), ex.getMessage());

    return ResponseEntity.badRequest().body(responseErrorModel);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<ErrorModel> handleMethodArgumentNotValid(
      MethodArgumentNotValidException maEx) {
    log.warn("[VALIDATION ERROR] - {}", maEx.getMessage());
    final var errors = maEx.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(","));
    final var errorCode = ErrorCodeEnum.VALIDATION_DATA_ERROR;
    final var responseErrorModel =
        new ErrorModel(errorCode.getErrorCode(), errorCode.getErrorMessage(), errors);

    return ResponseEntity.badRequest().body(responseErrorModel);
  }

  @ExceptionHandler(DataAccessException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorModel> handleDataAccess(DataAccessException daEx) {
    log.warn("[INTERNAL ERROR] - {}", daEx.getMessage());
    final var errorCode = ErrorCodeEnum.DATABASE_OPERATION_ERROR;
    final var responseErrorModel =
        new ErrorModel(errorCode.getErrorCode(), errorCode.getErrorMessage(), daEx.getMessage());

    return ResponseEntity.internalServerError().body(responseErrorModel);
  }

}
