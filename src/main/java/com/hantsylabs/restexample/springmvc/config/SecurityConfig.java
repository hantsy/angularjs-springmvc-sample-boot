package com.hantsylabs.restexample.springmvc.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.NullSecurityContextRepository;

import com.hantsylabs.restexample.springmvc.security.SimpleUserDetailsServiceImpl;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private SimpleUserDetailsServiceImpl userDetailsService;	

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
                    .ignoring()
			.antMatchers("/**/*.html", 
                                "/css/**", 
                                "/js/**", 
                                "/i18n/**", 
                                "/libs/**",
                                "/img/**", 
                                "/ico/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
                        .antMatchers("/api/public/**")
			.permitAll()
			.antMatchers("/api/**")
			.authenticated()
			.anyRequest()
			.permitAll()
                    .and()
                        .securityContext()
                        .securityContextRepository(new NullSecurityContextRepository())
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
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider _authenticationProvider = new DaoAuthenticationProvider();
		_authenticationProvider.setPasswordEncoder(passwordEncoder());
		_authenticationProvider.setUserDetailsService(userDetailsService);

		return _authenticationProvider;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

}
