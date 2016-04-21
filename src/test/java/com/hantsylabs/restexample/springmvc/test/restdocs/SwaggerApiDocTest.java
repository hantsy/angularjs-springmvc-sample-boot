package com.hantsylabs.restexample.springmvc.test.restdocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;
import springfox.documentation.staticdocs.SwaggerResultHandler;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@Ignore
public class SwaggerApiDocTest {

    final String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");

    final String snippetsDir = System.getProperty("org.springframework.restdocs.outputDir");

    @Inject
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void createSwaggerJson() throws Exception {
        this.mockMvc
                .perform(
                        get("/v2/api-docs")
                        .accept(MediaType.ALL)
                )
                .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
                .andExpect(status().isOk());
    }

    @Test
    public void convertSwaggerToAsciiDoc() throws Exception {
        this.mockMvc
                .perform(
                        get("/v2/api-docs")
                        .accept(MediaType.ALL)
                )
                .andDo(
                        Swagger2MarkupResultHandler
                        .outputDirectory(outputDir)
                        .withExamples(snippetsDir)
                        .build()
                )
                .andExpect(
                        status()
                        .isOk()
                );
    }

}
