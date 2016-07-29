package com.hantsylabs.restexample.springmvc.test.slice;

import com.hantsylabs.restexample.springmvc.model.PostDetails;
import com.hantsylabs.restexample.springmvc.test.slice.SimpleRestClientTest.ClientBlogService;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@RestClientTest(ClientBlogService.class)
@Slf4j
// @AutoConfigureWebClient(registerRestTemplate=true)
public class SimpleRestClientTest {

    @Inject
    private ClientBlogService service;

//    @Inject
//    RestTemplateBuilder builder;
    @Inject
    private MockRestServiceServer server;

    @Test
    public void testGetPostById() throws Exception {
        this.server.expect(requestTo("/api/posts/1"))
                .andRespond(withSuccess("{\"id\":\"1\",\"title\":\"test title\",\"content\":\"test content\"}", MediaType.APPLICATION_JSON));
        PostDetails post = this.service.getPostById(1L);
        assertThat(post.getTitle()).isEqualTo("test title");
    }

    @TestComponent
    static class ClientBlogService {

        RestTemplate restTemplate;

        @Inject
        public ClientBlogService(RestTemplateBuilder builder) {
            this.restTemplate = builder.build();
        }

        public PostDetails getPostById(Long id) {
            ResponseEntity<PostDetails> postResponse = this.restTemplate.getForEntity("/api/posts/" + id, PostDetails.class);
            return postResponse.getBody();
        }

    }

}
