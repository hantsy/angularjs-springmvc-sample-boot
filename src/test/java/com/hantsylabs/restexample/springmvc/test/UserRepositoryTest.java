/*
 * Copyright 2015 Pivotal Software, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.Application;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
    @Inject
    private UserRepository userRepository;
    
    public UserRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
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
