package com.hantsylabs.restexample.springmvc;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.hantsylabs.restexample.springmvc.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile(value = {"dev", "test", "staging"})// Loads the spring beans required by the framework
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

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
        ArrayList<SecurityContext> securityContexts = Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(Lists.newArrayList(securityReference))
                        .build()
        );
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .ignoredParameterTypes(User.class)
                .securitySchemes(Lists.newArrayList(new BasicAuth("test")))
                .securityContexts(securityContexts)
                .apiInfo(apiInfo())
                .select()
                .paths(apiPaths())
                .build();
    }

    private Predicate<String> apiPaths() {
        return Predicates.or(PathSelectors.regex("/api/.*"));
    }

    //        private Predicate<String> userOnlyEndpoints() {
    //            return (String input) -> input.contains("user");
    //        }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AngularJS Spring MVC Example API")
                .description("The online reference documentation for developers")
                .termsOfServiceUrl("http://hantsy.blogspot.com")
                .contact(new Contact("Hantsy Bai", "http://hantsy.blogspot.com", "hantsy@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/hantsy/angularjs-springmvc-sample-boot/blob/master/LICENSE")
                .version("2.0")
                .build();
    }

}
