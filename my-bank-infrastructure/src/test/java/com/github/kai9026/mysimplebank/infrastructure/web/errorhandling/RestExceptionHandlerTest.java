package com.github.kai9026.mysimplebank.infrastructure.web.errorhandling;

import static com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum.DATABASE_OPERATION_ERROR;
import static com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorCodeEnum.VALIDATION_DATA_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.github.kai9026.mysimplebank.application.exception.InvalidInputDataException;
import com.github.kai9026.mysimplebank.infrastructure.web.errorhandling.model.ErrorModel;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

  private final RestExceptionHandler exceptionHandler = new RestExceptionHandler();

  @Test
  @DisplayName("Test handling invalid data, expect return response with error model")
  void handlingException_invalidDataException_shouldReturnErrorModel() {

    final InvalidInputDataException invalidInputDataException =
        new InvalidInputDataException("email is required");

    final var errorModelResponseEntity =
        this.exceptionHandler.handleInvalidInputData(invalidInputDataException);

    assertThat(errorModelResponseEntity).isNotNull();
    assertThat(errorModelResponseEntity.getStatusCode())
        .isEqualTo(BAD_REQUEST);
    assertThat(errorModelResponseEntity.getBody()).isNotNull();
    assertThat(errorModelResponseEntity.getBody().errorCode())
        .isEqualTo(VALIDATION_DATA_ERROR.getErrorCode());
    assertThat(errorModelResponseEntity.getBody().message())
        .isEqualTo(VALIDATION_DATA_ERROR.getErrorMessage());
  }

  @Test
  @DisplayName("Test handling database errors, expect return response with error model")
  void handlingException_invalidDatabaseErrorsException_shouldReturnErrorModel() {
    final var dataAccessException = new DuplicateKeyException("database error");

    final var errorModelResponseEntity =
        this.exceptionHandler.handleDataAccess(dataAccessException);

    assertThat(errorModelResponseEntity).isNotNull();
    assertThat(errorModelResponseEntity.getStatusCode())
        .isEqualTo(INTERNAL_SERVER_ERROR);
    assertThat(errorModelResponseEntity.getBody()).isNotNull();
    assertThat(errorModelResponseEntity.getBody().errorCode())
        .isEqualTo(DATABASE_OPERATION_ERROR.getErrorCode());
    assertThat(errorModelResponseEntity.getBody().message())
        .isEqualTo(DATABASE_OPERATION_ERROR.getErrorMessage());
  }
}