
package com.hantsylabs.restexample.springmvc.exception;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class UsernameExistedException extends RuntimeException {

    private String username;

    public UsernameExistedException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
}
