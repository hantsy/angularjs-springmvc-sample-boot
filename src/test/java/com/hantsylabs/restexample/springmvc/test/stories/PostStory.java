package com.hantsylabs.restexample.springmvc.test.stories;

import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.test.AbstractSpringJBehaveStory;
import com.hantsylabs.restexample.springmvc.test.AcceptanceTest;
import javax.inject.Inject;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AcceptanceTest
public class PostStory extends AbstractSpringJBehaveStory {

    @Inject
    PostRepository postRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    ResponseEntity<String> reponseEntity;

    @Given("post title is $title and content is $content")
    public void savePost(@Named("title") String title, @Named("content") String content) {
        postRepository.save(new Post(title, content));
    }

    @When("GET $path")
    public void getPost(@Named("path") String path) {
        reponseEntity = restTemplate.getForEntity(path, String.class);
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
