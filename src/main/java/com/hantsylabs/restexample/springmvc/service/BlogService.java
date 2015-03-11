/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hantsylabs.restexample.springmvc.service;

import com.hantsylabs.restexample.springmvc.DTOUtils;
import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.model.PostDetails;
import com.hantsylabs.restexample.springmvc.model.PostForm;
import com.hantsylabs.restexample.springmvc.repository.PostRepository;
import com.hantsylabs.restexample.springmvc.repository.PostSpecifications;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author hantsy
 */
@Service
@Transactional
public class BlogService {

    private static final Logger log = LoggerFactory.getLogger(BlogService.class);

    @Inject
    private PostRepository postRepository;

    public Page<PostDetails> findPostDetailsByKeyword(String q, Post.Status status, Pageable page) {
        if (log.isDebugEnabled()) {
            log.debug("search posts by keyword@" + q + ", page @" + page);
        }

        Page<Post> posts = postRepository.findAll(PostSpecifications.filterByKeywordAndStatus(q, status),
                page);

        if (log.isDebugEnabled()) {
            log.debug("get posts size @" + posts.getTotalElements());
        }

        return DTOUtils.mapPage(posts, PostDetails.class);
    }

    public PostDetails savePost(PostForm form) {
        if (log.isDebugEnabled()) {
            log.debug("save post @" + form);
        }

        Post post = DTOUtils.map(form, Post.class);

        Post saved = postRepository.save(post);

        if (log.isDebugEnabled()) {
            log.debug("saved post id is @" + saved);
        }

        return DTOUtils.map(post, PostDetails.class);

    }

    public void updatePost(Long id, PostForm form) {
        Assert.notNull(id, "post id can not be null");

        if (log.isDebugEnabled()) {
            log.debug("update post @" + form);
        }

        Post post = postRepository.findOne(id);
        DTOUtils.mapTo(form, post);

        Post saved = postRepository.save(post);

        if (log.isDebugEnabled()) {
            log.debug("updated post@" + saved);
        }
    }

    public PostDetails findPostById(Long id) {
        Assert.notNull(id, "post id can not be null");

        if (log.isDebugEnabled()) {
            log.debug("find post by id@" + id);
        }

        Post post = postRepository.findOne(id);

        if (post == null) {
            throw new ResourceNotFoundException(id);
        }

        return DTOUtils.map(post, PostDetails.class);
    }

}
