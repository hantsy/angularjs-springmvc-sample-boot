package com.hantsylabs.restexample.springmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 *
 * @author hantsy
 */
@Configuration
public class SecurityConfig {
    
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigure(){
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
}
