package com.aladdin.personalbudgetcontrollerdemo2.exception;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Object... args) {
    }

    public static ResourceNotFoundException of(String message, Object... args) {
        return new ResourceNotFoundException(MessageFormat.format(message, args));
    }
}
