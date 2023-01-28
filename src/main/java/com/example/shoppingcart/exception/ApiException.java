package com.example.shoppingcart.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final List<String> details;

    public ApiException(String message,
                        HttpStatus httpStatus,
                        ZonedDateTime timestamp, List<String> details) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.details=details;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getDetails() {
        return details;
    }
}
