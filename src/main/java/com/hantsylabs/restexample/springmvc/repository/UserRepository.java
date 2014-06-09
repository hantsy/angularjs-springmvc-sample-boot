package com.hantsylabs.restexample.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.restexample.springmvc.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByUsername(String username);

}
