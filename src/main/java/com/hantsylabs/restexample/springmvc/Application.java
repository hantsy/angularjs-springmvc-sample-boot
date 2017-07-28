package com.hantsylabs.restexample.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

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
