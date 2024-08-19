package org.example.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public BadResponse handleException(UserAlreadyExistsException ex) {
        return createBadResponse(List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserParameterNotFoundException.class)
    public BadResponse handleException(UserParameterNotFoundException ex) {
        return createBadResponse(List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BadResponse handleException(MethodArgumentNotValidException ex) {
        return createBadResponse(ex.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList());
    }

    private BadResponse createBadResponse(List<String> messages) {
        return BadResponse.builder()
                .messages(messages)
                .build();
    }
}
