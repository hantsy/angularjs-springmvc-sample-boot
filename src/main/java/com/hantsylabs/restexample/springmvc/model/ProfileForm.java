
package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class ProfileForm implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
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
