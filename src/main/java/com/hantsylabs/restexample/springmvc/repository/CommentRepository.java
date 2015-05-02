package com.hantsylabs.restexample.springmvc.repository;

import com.hantsylabs.restexample.springmvc.domain.Comment;
import com.hantsylabs.restexample.springmvc.domain.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByPost(Post post);

    public Page<Comment> findByPostId(Long id, Pageable page);
	
}
