package com.hantsylabs.restexample.springmvc.service;

import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

@Named
public class DataImporter implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory
            .getLogger(DataImporter.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("importing data into database...");
        }

        Long usersCount = userRepository.count();
        if (usersCount == 0) {
            if (log.isDebugEnabled()) {
                log.debug("import users data into database...");
            }
            userRepository.save(
                    User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("test123"))
                    .name("Administrator")
                    .role("ADMIN")
                    .build()
            );
            userRepository.save(
                    User.builder()
                    .username("testuser")
                    .password(passwordEncoder.encode("test123"))
                    .name("Test User")
                    .role("USER")
                    .build()
            );

        }

    }

}
