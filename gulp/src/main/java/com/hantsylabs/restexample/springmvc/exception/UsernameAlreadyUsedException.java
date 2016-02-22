
package com.hantsylabs.restexample.springmvc.exception;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String username;

    public UsernameAlreadyUsedException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
}
