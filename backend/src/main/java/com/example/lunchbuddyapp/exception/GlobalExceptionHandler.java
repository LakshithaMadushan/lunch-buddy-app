package com.example.lunchbuddyapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({UnAuthorizeException.class, UserIdAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlerOne(Exception ex, WebRequest webRequest) {
        log.info(ex.getMessage());
        log.info(webRequest.getDescription(true));
        log.info(webRequest.getContextPath());

        return new ResponseEntity<>("Global handler", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlerTwo(Exception ex, WebRequest webRequest) {
        log.info(ex.getMessage());
        log.info(webRequest.getDescription(true));
        log.info(webRequest.getContextPath());

        return new ResponseEntity<>("Global handler for runtime", HttpStatus.BAD_REQUEST);
    }
}
