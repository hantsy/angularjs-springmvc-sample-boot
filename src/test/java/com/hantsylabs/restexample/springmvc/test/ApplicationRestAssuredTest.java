package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.model.PostForm;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import static com.jayway.restassured.RestAssured.given;
import org.springframework.beans.factory.annotation.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationRestAssuredTest {

    private static final String USER_NAME = "admin";

    private final static String PASSWORD = "test123";

    @Value("${local.server.port}")
    protected int port;

    @Before
    public void beforeTest() {
        RestAssured.port = port;
    }

    @Test
    public void testCreatePost() {
        PostForm form = new PostForm();
        form.setTitle("test title");
        form.setContent("test content");
              
        given()
            .auth().basic(USER_NAME, PASSWORD)
            .body(form)
            .contentType(ContentType.JSON)
        .when()
            .post("/api/posts")
        .then()
            .statusCode(HttpStatus.SC_CREATED);
    }

}
