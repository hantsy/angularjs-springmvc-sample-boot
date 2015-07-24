
package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class UserDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String username;

    private String name;

    private String email;

    private String role;
    
    private LocalDateTime createdDate;

    
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
   
    @Override
    public String toString() {
        return "UserDetails{" + "username=" + username + ", name=" + name + ", email=" + email + ", role=" + role + '}';
    }

}
