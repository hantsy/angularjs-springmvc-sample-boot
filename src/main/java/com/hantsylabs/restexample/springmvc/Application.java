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
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


  

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
    
//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository){
//        return new SimpleUserDetailsServiceImpl(userRepository);
//    }
    


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
