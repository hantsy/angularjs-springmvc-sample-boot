
package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class SimpleUserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SimpleUserDetails{" + "id=" + id + ", username=" + username + ", name=" + name + '}';
    }

}
