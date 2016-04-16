package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.Application;
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
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;

import org.springframework.restdocs.RestDocumentation;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;
import springfox.documentation.staticdocs.SwaggerResultHandler;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@IntegrationTest
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
