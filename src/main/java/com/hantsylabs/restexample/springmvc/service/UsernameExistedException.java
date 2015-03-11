/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hantsylabs.restexample.springmvc.service;

/**
 *
 * @author hantsy
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
