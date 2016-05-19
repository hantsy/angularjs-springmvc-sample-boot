package com.hantsylabs.restexample.springmvc.test.jbehave.stories;

import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.test.jbehave.AcceptanceTest;
import com.hantsylabs.restexample.springmvc.test.jbehave.AbstractSpringJBehaveStory;
import com.hantsylabs.restexample.springmvc.test.jbehave.steps.PostSteps;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AcceptanceTest
public class PostStory extends AbstractSpringJBehaveStory {

    @Inject
    PostRepository postRepository;

    TestRestTemplate restTemplate;

    String baseUrl;

    @LocalServerPort
    int port;

    @Before
    public void setup() {
        clearData();
        initData();
        this.baseUrl = "http://localhost:" + port;
        this.restTemplate = new TestRestTemplate("admin", "test123", new HttpClientOption[]{});
    }

    @Override
    public Object[] getSteps() {
        return new Object[]{new PostSteps(postRepository, restTemplate, baseUrl)};
    }

}
