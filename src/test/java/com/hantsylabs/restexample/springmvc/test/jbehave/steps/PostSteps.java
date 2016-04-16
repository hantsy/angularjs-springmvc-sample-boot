package com.hantsylabs.restexample.springmvc.test.jbehave.steps;

import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import static org.junit.Assert.assertTrue;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.ResponseEntity;

public class PostSteps {

    PostRepository postRepository;

    TestRestTemplate restTemplate;

    String baseUrl;

    public PostSteps(PostRepository postRepository,
            TestRestTemplate restTemplate,
            String baseUrl) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    ResponseEntity<String> reponseEntity;

    @Given("post title is $title and content is $content")
    public void savePost(@Named("title") String title, @Named("content") String content) {
        postRepository.save(new Post(title, content));
    }

    @When("GET $path")
    public void getPost(@Named("path") String path) {
        reponseEntity = restTemplate.getForEntity(baseUrl + path, String.class);
    }

    @Then("response status is $code")
    public void responseCode(@Named("code") int code) {
        assertTrue(reponseEntity.getStatusCode().value() == code);
    }

    @Then("response body contains $body")
    public void responseBody(@Named("body") String body) {
        assertTrue(reponseEntity.getBody().contains(body));
    }

}
