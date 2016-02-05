package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserRepositoryTest {
    
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
    
    @Inject TestUtils utils;
    
    @Inject
    private UserRepository userRepository;
    
    public UserRepositoryTest() {
    }
    
    
    private User newUser() {
        User user = new User();
        user.setName("test user" + new Random().nextInt(10));
        user.setEmail("hantsy@gmail.com");
        user.setPassword("pwd");
        user.setRole("USER");
        user.setUsername("test");
        
        return user;
    }
    
    @Before
    @Transactional
    public void setUp() {
          utils.clearData();
        userRepository.save(newUser());
        userRepository.save(newUser());
        userRepository.save(newUser());
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.debug("users @" + users);
        assertTrue("user's size is 3", users.size() == 3);   
    }
}
