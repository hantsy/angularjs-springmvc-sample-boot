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
