package com.hantsylabs.restexample.springmvc.test.mock;

import com.hantsylabs.restexample.springmvc.domain.Comment;
import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.exception.ResourceNotFoundException;
import com.hantsylabs.restexample.springmvc.model.CommentDetails;
import com.hantsylabs.restexample.springmvc.model.CommentForm;
import com.hantsylabs.restexample.springmvc.model.PostDetails;
import com.hantsylabs.restexample.springmvc.model.PostForm;
import com.hantsylabs.restexample.springmvc.repository.CommentRepository;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.repository.PostSpecifications;
import com.hantsylabs.restexample.springmvc.service.BlogService;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MockBlogServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    private BlogService blogService;

    public MockBlogServiceTest() {
    }

    @Before
    public void setUp() {
        //only need when using other Runner, such as SpringRunner. @RunWith(MockitoJUnitRunner.class) does the same work.
        //MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFetchPostsByPage() {
        PageRequest pr = new PageRequest(0, 10);
        Post post = Post.builder().id(1L).title("test post title").content("test post content@").build();
        when(postRepository.findAll(PostSpecifications.filterByKeywordAndStatus(any(String.class), any(Post.Status.class)), pr))
                .thenAnswer((InvocationOnMock invocation) -> new PageImpl<Post>(Arrays.asList(post), pr, 1));

        Page<PostDetails> pagedPosts = blogService.searchPostsByCriteria("", Post.Status.DRAFT, pr);
        log.debug("posts @" + pagedPosts.getContent());
        assertTrue("posts's size is 1", pagedPosts.getTotalElements() == 1);

        PostDetails result = pagedPosts.getContent().get(0);

        log.debug("paged poast details #1 @" + result);

        assertTrue(result.getTitle().equals("test post title"));
        assertTrue(result.getContent().startsWith("test post content@"));
    }

    @Test
    public void testSavePost() {
        PostForm form = new PostForm();
        form.setTitle("saving title");
        form.setTitle("saving content");

        when(postRepository.save(any(Post.class)))
                .thenReturn(Post.builder().content("saving content").title("saving title").id(1L).build());

        PostDetails result = blogService.savePost(form);

        assertNotNull("saved post id should not be null@", result.getId());
        assertTrue(result.getTitle().equals("saving title"));
        assertTrue(result.getContent().equals("saving content"));
    }

    @Test
    public void testUpdatePost() {
        PostForm form = new PostForm();
        form.setTitle("updating title");
        form.setContent("updating content");

        Post post = Post.builder().content("updating content").title("updating title").id(1L).build();
        when(postRepository.findOne(any(Long.class)))
                .thenReturn(post);

        when(postRepository.save(any(Post.class)))
                .thenReturn(post);

        PostDetails details = blogService.updatePost(1L, form);

        assertNotNull("saved post id should not be null@", details.getId());

        assertEquals("updating title", details.getTitle());
        assertEquals("updating content", details.getContent());
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteById() {
        Post post = Post.builder().content("updating content").title("updating title").id(1L).build();
        when(postRepository.findOne(any(Long.class)))
                .thenReturn(post);
        doThrow(new RuntimeException()).when(postRepository).delete(any(Post.class));
        blogService.deletePostById(1L);
        
        verify(postRepository, times(2));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testException() {
        when(postRepository.findOne(any(Long.class)))
                .thenReturn(null);
        blogService.findPostById(1L);
    }

    @Test()
    public void testAddCommentPost() {
        CommentForm form = new CommentForm();
        form.setContent("test comment");

        Post post = Post.builder().id(1L).title("test post title").content("test post content@").build();
        when(postRepository.findOne(any(Long.class)))
                .thenReturn(post);

        Comment comment = Comment.builder().post(post).content("test comment").id(1L).build();

        when(commentRepository.save(comment))
                .thenReturn(comment);

        CommentDetails commentDetails = blogService.saveCommentOfPost(1L, form);

        assertTrue(commentDetails.getId() != null);
        assertTrue("test comment".equals(commentDetails.getContent()));
        
    }

}
