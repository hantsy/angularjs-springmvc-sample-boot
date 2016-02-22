
package com.hantsylabs.restexample.springmvc.exception;

import org.springframework.validation.BindingResult;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class InvalidRequestException extends RuntimeException {

    private BindingResult errors;

    public InvalidRequestException(BindingResult errors) {
        this.errors = errors;
    }

    public BindingResult getErrors() {
        return errors;
    }

}
