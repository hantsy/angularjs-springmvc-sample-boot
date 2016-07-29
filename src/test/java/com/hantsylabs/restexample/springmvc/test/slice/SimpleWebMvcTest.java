package com.hantsylabs.restexample.springmvc.test.slice;

import com.hantsylabs.restexample.springmvc.api.post.PostController;
import com.hantsylabs.restexample.springmvc.model.PostDetails;
import com.hantsylabs.restexample.springmvc.service.BlogService;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostController.class, secure = false)
@Slf4j
public class SimpleWebMvcTest {

    @MockBean
    private BlogService blogService;

    @Inject
    private MockMvc mockMvc;
    
//    @Autowired
//    private WebClient webClient;

    @Before
    public void setUp() {
        // this.mockMvc = standaloneSetup(new PostController(blogService)).build();
    }

    @Test
    public void testGetPostById() throws Exception {
        given(this.blogService.findPostById(1L))
            .willReturn(PostDetails.builder().id(1L).title("title").content("test content").build());

        this.mockMvc
            .perform(
                get("/api/posts/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

}
