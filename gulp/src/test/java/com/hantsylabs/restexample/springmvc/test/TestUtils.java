package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.CommentRepository;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hantsy
 */
@Component
@Transactional
@Slf4j
public class TestUtils {

    @Inject
    PostRepository posts;

    @Inject
    CommentRepository comments;

    @Inject
    UserRepository users;

    @Inject
    PasswordEncoder passwordEncoder;

    public void clearData() {
        log.debug("clearing data...");

        comments.deleteAllInBatch();
        posts.deleteAllInBatch();
        users.deleteAllInBatch();
    }

    public void initData() {

        users.save(
                User.builder()
                .username("admin")
                .password(passwordEncoder.encode("test123"))
                .name("Administrator")
                .role("ADMIN")
                .build()
        );
    }

}
