package com.hantsylabs.restexample.springmvc.repository;

import com.hantsylabs.restexample.springmvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    public User findByUsername(String username);
}
