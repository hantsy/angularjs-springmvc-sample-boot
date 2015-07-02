/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.hantsylabs.restexample.springmvc.config;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
@Configuration
@EnableSwagger2
// Loads the spring beans required by the framework
@Profile(value = {"dev", "staging"})
public class SwaggerConfig {

    @Bean
    public Docket publicApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("public-api")
            .apiInfo(apiInfo())
            .select()
            .paths(publicPaths())
            .build();
    }

    @Bean
    public Docket managementApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("management-api")
            .apiInfo(apiInfo())
            .select()
            .paths(managementPaths())
            .build();
    }

    @Bean
    public Docket userApi() {
        AuthorizationScope[] authScopes = new AuthorizationScope[1];
        authScopes[0] = new AuthorizationScopeBuilder()
            .scope("read")
            .description("read access")
            .build();
        SecurityReference securityReference = SecurityReference.builder()
            .reference("test")
            .scopes(authScopes)
            .build();

        ArrayList<SecurityContext> securityContexts = newArrayList(SecurityContext.builder().securityReferences(newArrayList(securityReference)).build());
        return new Docket(DocumentationType.SWAGGER_2)
            .securitySchemes(newArrayList(new BasicAuth("test")))
            .securityContexts(securityContexts)
            .groupName("user-api")
            .apiInfo(apiInfo())
            .select()
            .paths(userOnlyEndpoints())
            .build();
    }

    private Predicate<String> publicPaths() {
        return or(
            regex("/api/public.*")
        );
    }

    private Predicate<String> managementPaths() {
        return or(
            regex("/api/mgt.*")
        );
    }

    private Predicate<String> userOnlyEndpoints() {
        return new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.contains("user");
            }
        };
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("SpringMVC Example API")
            .description("SpringMVC Example API reference for developers")
            .termsOfServiceUrl("http://hantsy.blogspot.com")
            .contact("Hantsy Bai")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
            .version("2.0")
            .build();
    }

}
