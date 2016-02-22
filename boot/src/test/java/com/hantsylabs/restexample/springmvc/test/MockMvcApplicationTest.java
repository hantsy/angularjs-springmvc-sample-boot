package com.hantsylabs.restexample.springmvc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import org.junit.Rule;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.restdocs.RestDocumentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class MockMvcApplicationTest {

    final String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");

    final String snippetsDir = System.getProperty("org.springframework.restdocs.outputDir");

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation(snippetsDir);

    @Inject
    private WebApplicationContext context;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private PostRepository postRepository;

    private MockMvc mockMvc;

    private Post savedIdentity;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        postRepository.deleteAllInBatch();
        savedIdentity = postRepository.save(newEntity());
    }

    @Test
    public void getAllPosts() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/posts/{id}", savedIdentity.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(document("get_a_post"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllIdentities() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/posts")
                        .accept(MediaType.ALL)
                )
                .andDo(document("get_all_posts"))
                .andExpect(status().isOk());
    }

    @Test
    public void createPost() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEntityAsJson())
                )
                .andDo(document("create_a_new_post"))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePost() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/posts/{id}", savedIdentity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEntityAsJson())
                )
                .andDo(document("update_an_existing_post"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePost() throws Exception {
        this.mockMvc
                .perform(
                        delete("/api/posts/{id}", savedIdentity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(document("delete_an_existing_post"))
                .andExpect(status().isNoContent());
    }

    private Post newEntity() {
        Post post = new Post();
        post.setTitle("test title");
        post.setContent("test content");

        return post;
    }

    private String newEntityAsJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(newEntity());
    }

}
