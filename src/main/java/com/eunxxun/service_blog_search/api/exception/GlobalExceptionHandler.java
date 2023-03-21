package com.eunxxun.service_blog_search.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();

        ExceptionResponse<String> exceptionResponse = new ExceptionResponse<>(e.toString());
        exceptionResponse.setCode(e.getStatusCode().value());
        exceptionResponse.setMessage(message);
        log.error("MethodArgumentNotValidException : " + message);

        return exceptionResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse<?> internalServerException(Exception e) {
        ExceptionResponse<String> exceptionResponse = new ExceptionResponse<>(e.toString());
        exceptionResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        exceptionResponse.setMessage(e.getMessage());
        log.error("internalServerException : " + e.getMessage());

        return exceptionResponse;
    }
}
