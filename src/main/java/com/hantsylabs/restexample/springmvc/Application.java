package com.hantsylabs.restexample.springmvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.hantsylabs.restexample.springmvc.security.SecurityUtil;
import com.hantsylabs.restexample.springmvc.security.SimpleUserDetailsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public Jackson2ObjectMapperBuilder objectMapperBuilder(JsonComponentModule jsonComponentModule) {
   
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder
//                .serializerByType(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
//                    @Override
//                    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
//                        jsonGenerator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
//                    }
//                })
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .indentOutput(true)
                .modulesToInstall(jsonComponentModule);
    
        return builder;
    }   

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new SimpleUserDetailsServiceImpl(userRepository);
    }
    
    @Bean
    public WebSecurityConfigurerAdapter securityConfig(){
        return new WebSecurityConfigurerAdapter() {
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
        };
    }

//    @Configuration
//    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
//
//        private final UserRepository userRepository;
//        private final PasswordEncoder passwordEncoder;
//
//        //@Inject
//        ApplicationSecurity(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//            this.userRepository = userRepository;
//            this.passwordEncoder = passwordEncoder;
////        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//            http
//                .authorizeRequests()
//                .antMatchers("/api/signup", "/api/users/username-check")
//                .permitAll()
//                .and()
//                    .authorizeRequests()
//                    .regexMatchers(HttpMethod.GET, "^/api/users/[\\d]*(\\/)?$").authenticated()
//                    .regexMatchers(HttpMethod.GET, "^/api/users(\\/)?(\\?.+)?$").hasRole("ADMIN")
//                    .regexMatchers(HttpMethod.DELETE, "^/api/users/[\\d]*(\\/)?$").hasRole("ADMIN")
//                    .regexMatchers(HttpMethod.POST, "^/api/users(\\/)?$").hasRole("ADMIN")
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/api/**").authenticated()
//                .and()
//                    .authorizeRequests()
//                    .anyRequest().permitAll()
//                .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                    .httpBasic()
//                .and()
//                    .csrf()
//                    .disable();
//        // @formatter:on
//        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth)
//                throws Exception {
//            auth
//                    .userDetailsService(new SimpleUserDetailsServiceImpl(userRepository))
//                    .passwordEncoder(passwordEncoder);
//        }
//
//        @Bean
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
//
//       
//
//    }
}
