package com.aladdin.personalbudgetcontrollerdemo2.model.dto;

public record ErrorResponse(
        int status,
        String message,
        String details,
        String errorTime
) {
}