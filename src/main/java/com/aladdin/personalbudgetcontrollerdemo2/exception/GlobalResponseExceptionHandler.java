package com.aladdin.personalbudgetcontrollerdemo2.exception;

import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.rmi.AlreadyBoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalResponseExceptionHandler {

    private LocalDateTime now = LocalDateTime.now();


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> responseHandler(RuntimeException ex, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");
        String formattedDate = now.format(formatter);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false),
                formattedDate
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyBoundException.class)
    public ResponseEntity<ErrorResponse> responseHandler(AlreadyBoundException ex, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");
        String formattedDate = now.format(formatter);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getDescription(false),
                formattedDate
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
