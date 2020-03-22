package ru.kk.web.controllers.exhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.kk.exception.BusinessException;
import ru.kk.exception.ShortUrlNotFoundException;
import ru.kk.exception.UrlValidatedException;
import ru.kk.web.dto.ErrorType;
import ru.kk.web.dto.response.ErrorResponseDto;

import javax.validation.ConstraintViolationException;

import static ru.kk.constants.ErrorMessages.BAD_REQUEST_ERROR;
import static ru.kk.constants.ErrorMessages.UNKNOWN_RUNTIME_EXCEPTION;
import static ru.kk.web.dto.ErrorType.*;

@Slf4j
@ControllerAdvice
public class ControllersExceptionsHandler {

    @ExceptionHandler(ShortUrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpEntity<ErrorResponseDto> handleNotFoundException(BusinessException ex) {
        return logAndCreateHttpEntity(ex, ex.getMessage(), ex.getClass().getName(), BUSINESS);
    }

    @ExceptionHandler(UrlValidatedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpEntity<ErrorResponseDto> handleUnprocessableEntityException(BusinessException ex) {
        return logAndCreateHttpEntity(ex, ex.getMessage(), ex.getClass().getName(), BUSINESS);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorResponseDto> hibernateValidationException(ConstraintViolationException ex) {
        return logAndCreateHttpEntity(ex, String.format(BAD_REQUEST_ERROR, ex.getMessage()), ex.toString(), VALIDATION);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorResponseDto> handleValidationException(MethodArgumentTypeMismatchException ex) {
        return logAndCreateHttpEntity(ex, String.format(BAD_REQUEST_ERROR, ex.getMessage()), ex.toString(), SYSTEM);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpEntity<ErrorResponseDto> handleRuntimeException(Exception ex) {
        return logAndCreateHttpEntity(
                ex, String.format(UNKNOWN_RUNTIME_EXCEPTION, ex.getMessage()), ex.toString(), SYSTEM);
    }

    private HttpEntity<ErrorResponseDto> logAndCreateHttpEntity(
            Exception ex, String message, String cause, ErrorType type) {

        if (!type.equals(BUSINESS)) {
            log.error(message, ex);
        }

        return new HttpEntity<>(new ErrorResponseDto(message, cause, type));
    }

}
