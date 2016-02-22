package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    @Size(min = 6, message = "username should be consist of 6 to 20 characters")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, message = "password should be consist of 6 to 20 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupForm{" + "username=" + username + ", password=" + password + '}';
    }

}
