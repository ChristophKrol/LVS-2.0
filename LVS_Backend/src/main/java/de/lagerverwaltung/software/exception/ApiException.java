package de.lagerverwaltung.software.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {
    private final String message;

    private final HttpStatus httpStatus;
    private final LocalDateTime timeStamp;

    public ApiException(String message, Throwable throwable, HttpStatus httpStatus, LocalDateTime timeStamp) {
        this.message = message;

        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }


    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
