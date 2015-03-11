package com.hantsylabs.restexample.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.restexample.springmvc.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
