package de.lagerverwaltung.software.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {NoSpaceAvailableException.class})
    public ResponseEntity<Object> handleNoSpaceAvailableException(NoSpaceAvailableException nae){
        //1. Create payload containing esception details
        ApiException apiException = new ApiException(
                nae.getMessage(),
                nae,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        //2. Resturn ResponseEntity

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {ContainerNotEmptyException.class})
    public ResponseEntity<Object> handleContainerNotEmptyException(ContainerNotEmptyException cnee){
        //1. Create payload containing esception details
        ApiException apiException = new ApiException(
                cnee.getMessage(),
                cnee,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        //2. Resturn ResponseEntity

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
