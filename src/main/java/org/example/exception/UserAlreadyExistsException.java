package org.example.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {

    public UserAlreadyExistsException(String ex) {
        super(ex);
    }
}
