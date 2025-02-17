package com.aladdin.personalbudgetcontrollerdemo2.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
