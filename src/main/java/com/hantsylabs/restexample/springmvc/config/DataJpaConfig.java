package com.hantsylabs.restexample.springmvc.config;

import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.hantsylabs.restexample.springmvc"})
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class DataJpaConfig {

    @Bean
    public AuditorAware<User> auditor() {
        return () -> SecurityUtil.currentUser();
    }

}
