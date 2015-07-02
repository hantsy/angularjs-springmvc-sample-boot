
package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class ProfileForm implements Serializable{
    
    private String name;
    
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserForm{" + "name=" + name + ", email=" + email + '}';
    }
}
