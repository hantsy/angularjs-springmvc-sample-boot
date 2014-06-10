package com.hantsylabs.restexample.springmvc.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Called when an exception occurs during request processing. Transforms the
 * exception message into JSON format.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<AlertMessage> handleAuthenticationException(AuthenticationException ex) {
        if (log.isDebugEnabled()) {
            log.debug("handling authentication exception...");
        }
        return new ResponseEntity<>(new AlertMessage(AlertMessage.Type.danger, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
       
}
