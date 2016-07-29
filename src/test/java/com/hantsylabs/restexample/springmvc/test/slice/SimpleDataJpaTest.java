package com.hantsylabs.restexample.springmvc.test.slice;

import com.hantsylabs.restexample.springmvc.repository.CommentRepository;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.test.Fixtures;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author hantsy
 */
@RunWith(SpringRunner.class)
@DataJpaTest()
@Slf4j
public class SimpleDataJpaTest {

    @Inject
    private TestEntityManager em;

    @Inject
    private PostRepository posts;
    
    @Inject
    private CommentRepository comments;

    @Before
    public void setUp() {
        comments.deleteAllInBatch();
        posts.deleteAllInBatch();
        em.persist(Fixtures.createPost(null, "title", "content"));
    }

    @Test
    public void testPostsGetByIdShouldReturnAPost() throws Exception {
        assertEquals(1, posts.findAll().size());
    }
}
