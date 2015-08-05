
package com.hantsylabs.restexample.springmvc.exception;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Long id;

    public ResourceNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
