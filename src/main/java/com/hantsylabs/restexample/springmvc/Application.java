package com.hantsylabs.restexample.springmvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.hantsylabs.restexample.springmvc.security.SecurityUtil;
import com.hantsylabs.restexample.springmvc.security.SimpleUserDetailsServiceImpl;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.inject.Inject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EntityScan(basePackageClasses = {User.class, Jsr310JpaConverters.class})
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuditorAware<User> auditor() {
        return () -> SecurityUtil.currentUser();
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder
                .serializerByType(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
                    @Override
                    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                        jsonGenerator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
                    }
                })
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .indentOutput(true);

        return builder;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public ApplicationSecurity securityConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new ApplicationSecurity(userRepository, passwordEncoder);
    }

//    @Configuration
//    @EnableSwagger2
//    @Profile(value = {"dev", "test", "staging"})// Loads the spring beans required by the framework
//    @AutoConfigureAfter(WebMvcAutoConfiguration.class)
    public static class SwaggerConfig {

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

            ArrayList<SecurityContext> securityContexts = newArrayList(
                    SecurityContext
                    .builder()
                    .securityReferences(newArrayList(securityReference))
                    .build()
            );
            return new Docket(DocumentationType.SWAGGER_2)
                    .directModelSubstitute(LocalDateTime.class, String.class)
                    .ignoredParameterTypes(User.class)
                    .securitySchemes(newArrayList(new BasicAuth("test")))
                    .securityContexts(securityContexts)
                    .apiInfo(apiInfo())
                    .select()
                    .paths(apiPaths())
                    .build();
        }

        private Predicate<String> apiPaths() {
            return or(
                    regex("/api/.*")
            );
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

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        ApplicationSecurity(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
            http
                .authorizeRequests()
                .antMatchers("/api/signup", "/api/users/username-check")
                .permitAll()
                .and()
                    .authorizeRequests()
                    .regexMatchers(HttpMethod.GET, "^/api/users/[\\d]*(\\/)?$").authenticated()
                    .regexMatchers(HttpMethod.GET, "^/api/users(\\/)?(\\?.+)?$").hasRole("ADMIN")
                    .regexMatchers(HttpMethod.DELETE, "^/api/users/[\\d]*(\\/)?$").hasRole("ADMIN")
                    .regexMatchers(HttpMethod.POST, "^/api/users(\\/)?$").hasRole("ADMIN")
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                .and()
                    .authorizeRequests()
                    .anyRequest().permitAll()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .httpBasic()
                .and()
                    .csrf()
                    .disable();
        // @formatter:on
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth)
                throws Exception {
            auth
                    .userDetailsService(new SimpleUserDetailsServiceImpl(userRepository))
                    .passwordEncoder(passwordEncoder);
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        @Override
        public UserDetailsService userDetailsServiceBean() throws Exception {
            return super.userDetailsServiceBean();
        }

    }
}
