/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.hantsylabs.restexample.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


/**
 *
 * @author hantsy
 */
@Configuration
@EnableSwagger
// Loads the spring beans required by the framework
@Profile(value = { "dev", "staging" })
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiVersion("V1.0")//
                .apiInfo(apiInfo())//
                .includePatterns(".*api.*")
                .genericModelSubstitutes(ResponseEntity.class, HttpEntity.class)
                .build();
        
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(//
                "Blog RESTful API",//
                "Blog RESTful APIs for developers", //
                "Blog API terms of service",//
                "hantsy@gmail.com",//
                "GPL Lisence", //
                "lisence URL");
        return apiInfo;
    }

}
