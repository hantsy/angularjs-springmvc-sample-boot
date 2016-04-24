package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.exception.PasswordMismatchedException;
import com.hantsylabs.restexample.springmvc.exception.ResourceNotFoundException;
import com.hantsylabs.restexample.springmvc.exception.UsernameAlreadyUsedException;
import com.hantsylabs.restexample.springmvc.model.UserDetails;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.hantsylabs.restexample.springmvc.service.UserService;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author hantsy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest extends IntegrationTestBase {

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    User user;

    @Before
    public void setUp() {
        super.setup();

        user = userRepository.save(Fixtures.createUser("test", "test"));
        //assertNotNull(user.getId());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSaveUser() {

        UserDetails user1 = userService.findUserById(user.getId());
        assertNotNull(user1.getId());
        assertTrue("test".equals(user1.getUsername()));

        UserDetails user2 = userService.updateUser(user.getId(), Fixtures.createUserForm("test1", "test1"));
        assertTrue("test1".equals(user2.getUsername()));

        userService.updatePassword(user.getId(), Fixtures.createPasswordForm("test1", "test-new"));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUserNotFound() {
        UserDetails user1 = userService.findUserById(1000L);
    }

    @Test(expected = UsernameAlreadyUsedException.class)
    public void testUserIsTake() {
        UserDetails user2 = userService.saveUser(Fixtures.createUserForm("test", "test1"));
    }

    @Test(expected = PasswordMismatchedException.class)
    public void testPasswordMismatched() {
        userService.updatePassword(user.getId(), Fixtures.createPasswordForm("test1111", "test-new"));
    }

}
