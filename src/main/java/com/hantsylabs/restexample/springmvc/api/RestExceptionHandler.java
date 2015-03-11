package com.hantsylabs.restexample.springmvc.api;

import com.hantsylabs.restexample.springmvc.model.ResponseMessage;
import com.hantsylabs.restexample.springmvc.service.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Called when an exception occurs during request processing. Transforms exception message into JSON format.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<ResponseMessage> handleAuthenticationException(Exception ex, WebRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("handling exception...");
        }
        return new ResponseEntity<>(new ResponseMessage(ResponseMessage.Type.danger, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("handling ResourceNotFoundException...");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
