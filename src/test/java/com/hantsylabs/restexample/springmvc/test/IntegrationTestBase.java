package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.repository.CommentRepository;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.jayway.restassured.RestAssured;
import javax.inject.Inject;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author hantsy
 */
public class IntegrationTestBase {

    protected static final String USER_NAME = "admin";

    protected final static String PASSWORD = "test123";

    @Inject
    PostRepository posts;

    @Inject
    CommentRepository comments;

    @Inject
    UserRepository users;

    @Inject
    PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        clearData();
    }

    public void clearData() {

        comments.deleteAllInBatch();
        posts.deleteAllInBatch();
        users.deleteAllInBatch();
    }

    public void initData() {

        users.save(
                com.hantsylabs.restexample.springmvc.domain.User.builder()
                .username(USER_NAME)
                .password(passwordEncoder.encode(PASSWORD))
                .name("Administrator")
                .role("ADMIN")
                .build()
        );
    }
}
