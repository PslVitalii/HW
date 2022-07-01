package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.exceptions.EntityAlreadyExistsException;
import com.epam.spring.homework3.exceptions.EntityNotFoundException;
import com.epam.spring.homework3.model.errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiError> handleMethodArgumentNotValid(BindException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.setDebugMessage(ex.getMessage());
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
        return buildResponseEntity(new ApiError(NOT_FOUND, e.getMessage(), e));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<ApiError> handleEntityAlreadyExists(EntityAlreadyExistsException e) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, e.getMessage(), e));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(BadCredentialsException e) {
        String error = "Invalid username or password";
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, e));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException e) {
        String error = "JWT token cannot be trusted";
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, e));
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
