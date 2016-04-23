package com.hantsylabs.restexample.springmvc.test.mockbean;

import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MockBeanTest {
    
    @MockBean
    private UserRepository userRepository;

    public MockBeanTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAllUsers() {
       assertNotNull(userRepository);
    }
}
