package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.exception.ResourceNotFoundException;
import com.hantsylabs.restexample.springmvc.model.CommentDetails;
import com.hantsylabs.restexample.springmvc.model.CommentForm;
import com.hantsylabs.restexample.springmvc.model.PostDetails;
import com.hantsylabs.restexample.springmvc.model.PostForm;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.service.BlogService;
import java.util.Random;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BlogServiceTest extends IntegrationTestBase {

    @Inject
    private PostRepository postRepository;

    @Inject
    private BlogService blogService;

    public BlogServiceTest() {
    }

    private Post newPost() {
        Post _post = new Post();
        _post.setTitle("test post title");
        _post.setContent("test post content@" + new Random().nextInt());

        return _post;
    }

    private Post post;

    @Before
    public void setUp() {
        super.setup();
        post = postRepository.save(newPost());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFetchPostsByPage() {
        PageRequest pr = new PageRequest(0, 10);
        Page<PostDetails> pagedPosts = blogService.searchPostsByCriteria("", Post.Status.DRAFT, pr);
        log.debug("posts @" + pagedPosts.getContent());
        assertTrue("posts's size is 1", pagedPosts.getTotalElements() == 1);

        PostDetails details = pagedPosts.getContent().get(0);

        log.debug("paged poast details #1 @" + details);

        assertTrue(details.getTitle().equals("test post title"));
        assertTrue(details.getContent().startsWith("test post content@"));
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

    @Test(expected = ResourceNotFoundException.class)
    public void testDeletePost() {

        blogService.deletePostById(post.getId());
        PostDetails findPostById = blogService.findPostById(post.getId());
    }

    @Test()
    public void testAddCommentPost() {
        CommentForm form = new CommentForm();
        form.setContent("test comment");
        CommentDetails commentDetails = blogService.saveCommentOfPost(post.getId(), form);

        assertTrue(commentDetails.getId() != null);
        assertTrue("test comment".equals(commentDetails.getContent()));
    }

}
