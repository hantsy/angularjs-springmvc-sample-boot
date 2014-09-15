package com.hantsylabs.restexample.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.restexample.springmvc.model.Comment;
import com.hantsylabs.restexample.springmvc.model.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByPost(Post post);
	
}
