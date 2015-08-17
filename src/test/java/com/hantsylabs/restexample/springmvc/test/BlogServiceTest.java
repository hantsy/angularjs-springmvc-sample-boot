package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.model.PostDetails;
import com.hantsylabs.restexample.springmvc.model.PostForm;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.service.BlogService;
import java.util.Random;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BlogServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceTest.class);

    @Inject
    private PostRepository postRepository;

    @Inject
    private BlogService blogService;

    public BlogServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    private Post newPost() {
        Post post = new Post();
        post.setTitle("test post title");
        post.setTitle("test post content@" + new Random().nextInt());

        return post;
    }

    private Post post;

    @Before
    public void setUp() {
        postRepository.deleteAllInBatch();
        post = postRepository.save(newPost());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFetchPostsByPage() {
        PageRequest pr = new PageRequest(0, 10);
        Page<PostDetails> pagedPosts = blogService.searchPostsByCriteria("", Post.Status.DRAFT, pr);
        logger.debug("posts @" + pagedPosts.getContent());
        assertTrue("posts's size is 1", pagedPosts.getTotalElements() == 1);
    }

    @Test
    public void testSavePost() {
        PostForm form = new PostForm();
        form.setTitle("saving title");
        form.setTitle("saving content");

        PostDetails details = blogService.savePost(form);

        assertNotNull("saved post id should not be null@", details.getId());
    }

    @Test
    public void testUpdatePost() {
        PostForm form = new PostForm();
        form.setTitle("updating title");
        form.setContent("updating content");

        PostDetails details = blogService.updatePost(post.getId(), form);

        assertNotNull("saved post id should not be null@", details.getId());
        
        assertEquals("post is should not be changed", post.getId(), details.getId());
        assertEquals("updating title", details.getTitle());
        assertEquals("updating content", details.getContent());
    }
}
