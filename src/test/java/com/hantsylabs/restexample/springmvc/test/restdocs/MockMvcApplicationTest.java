package com.hantsylabs.restexample.springmvc.test.restdocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.SwaggerConfig;
import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import org.junit.Rule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import springfox.documentation.staticdocs.SwaggerResultHandler;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, SwaggerConfig.class})
//@ActiveProfiles("test")
//@Ignore
public class MockMvcApplicationTest {

    String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");
    String snippetsDir = System.getProperty("io.springfox.staticdocs.snippetsOutputDir");
    String asciidocOutputDir = System.getProperty("generated.asciidoc.directory");

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(System.getProperty("io.springfox.staticdocs.snippetsOutputDir"));

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
                .alwaysDo(document("{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .build();

        savedIdentity = postRepository.save(newEntity());
    }

    @Test
    public void createSpringfoxSwaggerJson() throws Exception {
        //String designFirstSwaggerLocation = Swagger2MarkupTest.class.getResource("/swagger.yaml").getPath();

        MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(
                        SwaggerResultHandler.outputDirectory(outputDir)
                        .build()
                )
                .andExpect(status().isOk())
                .andReturn();

        //String springfoxSwaggerJson = mvcResult.getResponse().getContentAsString();
        //SwaggerAssertions.assertThat(Swagger20Parser.parse(springfoxSwaggerJson)).isEqualTo(designFirstSwaggerLocation);
    }

//    @Test
//    public void convertToAsciiDoc() throws Exception {
//        this.mockMvc.perform(get("/v2/api-docs")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(
//                        Swagger2MarkupResultHandler.outputDirectory("src/docs/asciidoc")
//                        .withExamples(snippetsDir).build())
//                .andExpect(status().isOk());
//    }
    @Test
    public void getAllPosts() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/posts/{id}", savedIdentity.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                //.andDo(document("get_a_post", preprocessResponse(prettyPrint())))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllIdentities() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/posts")
                        .accept(MediaType.ALL)
                )
                //.andDo(document("get_all_posts"))
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
                //.andDo(document("create_a_new_post"))
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
                //.andDo(document("update_an_existing_post"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePost() throws Exception {
        this.mockMvc
                .perform(
                        delete("/api/posts/{id}", savedIdentity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(document("delete_an_existing_post"))
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
