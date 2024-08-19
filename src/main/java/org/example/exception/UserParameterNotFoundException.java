package org.example.exception;

public class UserParameterNotFoundException extends RuntimeException {
    public UserParameterNotFoundException(String ex) {
        super(ex);
    }
}
