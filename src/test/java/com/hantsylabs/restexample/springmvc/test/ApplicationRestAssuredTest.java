package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.model.PostForm;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
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
import com.jayway.restassured.response.Response;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationRestAssuredTest {
    
    private static final Logger log = LoggerFactory.getLogger(ApplicationRestAssuredTest.class);

    private static final String USER_NAME = "admin";

    private final static String PASSWORD = "test123";
    
    @Inject
    PostRepository postRepository;

    @Value("${local.server.port}")
    protected int port;

    @Before
    public void beforeTest() {
        RestAssured.port = port;
        postRepository.deleteAllInBatch();
    }

    @Test
    public void testDeletePostNotExisted() {
        String location="/api/posts/1000";
        
        given()
                .auth().basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
            .when()
                .delete(location)
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }
    
    @Test
    public void testGetPostNotExisted() {
        String location="/api/posts/1000";
        
        given()
                .auth().basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
            .when()
                .get(location)
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }
    
    
    @Test
    public void testPostFormInValid() {
        PostForm form = new PostForm();

        given()
                .auth().basic(USER_NAME, PASSWORD)
                .body(form)
                .contentType(ContentType.JSON)
            .when()
                .post("/api/posts")
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    
    @Test
    public void testPostCRUD() {
        PostForm form = new PostForm();
        form.setTitle("test title");
        form.setContent("test content");

        Response response = given()
                .auth().basic(USER_NAME, PASSWORD)
                .body(form)
                .contentType(ContentType.JSON)
            .when()
                .post("/api/posts")
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_CREATED)
                .and()
                    .header("Location", containsString("/api/posts/"))
            .extract().response();
        
        String location = response.header("Location");
        
        log.debug("header location value @"+ location);
        
        given().auth().basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
            .when()
                .get(location)
            .then()
                .assertThat()
                    .body("title", is("test title"))
                    .body("content", is("test content"));
        
        
        
        PostForm updateForm = new PostForm();
        updateForm.setTitle("test udpate title");
        updateForm.setContent("test update content");

        given()
                .auth().basic(USER_NAME, PASSWORD)
                .body(updateForm)
                .contentType(ContentType.JSON)
            .when()
                .put(location)
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        
        given().auth().basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
            .when()
                .get(location)
            .then()
                .assertThat()
                    .body("title", is("test udpate title"))
                    .body("content", is("test update content"));
        
             
        given()
                .auth().basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
            .when()
                .delete(location)
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        
        given().auth().basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
            .when()
                .get(location)
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
        
    }


}
