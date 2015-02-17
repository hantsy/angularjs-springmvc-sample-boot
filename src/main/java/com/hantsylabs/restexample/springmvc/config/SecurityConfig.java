package com.hantsylabs.restexample.springmvc.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.NullSecurityContextRepository;

import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.hantsylabs.restexample.springmvc.security.SimpleUserDetailsServiceImpl;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private UserRepository userRepository;	

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
            .ignoring()
			.antMatchers("/**/*.html", //
                         "/css/**", //
                         "/js/**", //
                         "/i18n/**",// 
                         "/libs/**",//
                         "/img/**", //
                         "/ico/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .authorizeRequests()
                .antMatchers("/api/public/**")
                .permitAll()
            .and()    
                .authorizeRequests()   
    			.antMatchers("/api/**")
    			.authenticated()
    		.and()
    			.authorizeRequests()   
    			.anyRequest()
    			.permitAll()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .httpBasic()
            .and()
                .csrf()
                .disable();

                    // .formLogin().loginPage("/login").permitAll().and().logout()
                    // .permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(new SimpleUserDetailsServiceImpl(userRepository))
			.passwordEncoder(passwordEncoder());
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

//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider _authenticationProvider = new DaoAuthenticationProvider();
//		_authenticationProvider.setPasswordEncoder(passwordEncoder());
//		_authenticationProvider.setUserDetailsService(userDetailsService);
//
//		return _authenticationProvider;
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

}
