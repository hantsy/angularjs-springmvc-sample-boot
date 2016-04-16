package com.hantsylabs.restexample.springmvc.test.jbehave.stories;

import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.test.jbehave.AcceptanceTest;
import com.hantsylabs.restexample.springmvc.test.TestUtils;
import com.hantsylabs.restexample.springmvc.test.jbehave.AbstractSpringJBehaveStory;
import com.hantsylabs.restexample.springmvc.test.jbehave.steps.PostSteps;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AcceptanceTest
public class PostStory extends AbstractSpringJBehaveStory {

    @Inject
    PostRepository postRepository;

    @Inject
    TestUtils utils;

    TestRestTemplate restTemplate;

    String baseUrl;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setup() {
        utils.clearData();
        utils.initData();
        this.baseUrl = "http://localhost:" + port;
        this.restTemplate = new TestRestTemplate("admin", "test123", new HttpClientOption[]{});
    }

    @Override
    public Object[] getSteps() {
        return new Object[]{new PostSteps(postRepository, restTemplate, baseUrl)};
    }

}
