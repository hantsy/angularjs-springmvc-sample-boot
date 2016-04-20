package com.hantsylabs.restexample.springmvc.service;

import com.hantsylabs.restexample.springmvc.DTOUtils;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.exception.PasswordMismatchedException;
import com.hantsylabs.restexample.springmvc.exception.ResourceNotFoundException;
import com.hantsylabs.restexample.springmvc.exception.UsernameAlreadyUsedException;
import com.hantsylabs.restexample.springmvc.model.PasswordForm;
import com.hantsylabs.restexample.springmvc.model.ProfileForm;
import com.hantsylabs.restexample.springmvc.model.SignupForm;
import com.hantsylabs.restexample.springmvc.model.UserDetails;
import com.hantsylabs.restexample.springmvc.model.UserForm;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.hantsylabs.restexample.springmvc.repository.UserSpecifications;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    public static final String USER_ID_CAN_NOT_BE_NULL = "user id can not be null";
    public static final String UPDATED_USER = "updated user @";

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    public Page<UserDetails> findAll(String q, String role, Pageable page) {

        log.debug("find all users by keyword@" + q + ", role:" + role);

        Page<User> users = userRepository.findAll(UserSpecifications.filterUsersByKeyword(q, role), page);

        return DTOUtils.mapPage(users, UserDetails.class);
    }

    public UserDetails registerUser(SignupForm form) {
        Assert.notNull(form, " @@ SignupForm is null");

        log.debug("saving user@" + form);

        if (userRepository.findByUsername(form.getUsername()) != null) {
            throw new UsernameAlreadyUsedException(form.getUsername());
        }

        User user = DTOUtils.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        User saved = userRepository.save(user);

        //TODO sending an activation email.
        return DTOUtils.map(saved, UserDetails.class);
    }

    public UserDetails saveUser(UserForm form) {
        Assert.notNull(form, " @@ UserForm is null");

        log.debug("saving user@" + form);

        if (userRepository.findByUsername(form.getUsername()) != null) {
            throw new UsernameAlreadyUsedException(form.getUsername());
        }

        User user = DTOUtils.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        User saved = userRepository.save(user);

        return DTOUtils.map(saved, UserDetails.class);
    }

    public UserDetails updateUser(Long id, UserForm form) {
        Assert.notNull(id, USER_ID_CAN_NOT_BE_NULL);

        log.debug("update user by id @" + id);

        User user = userRepository.findOne(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        DTOUtils.mapTo(form, user);
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        User updated = userRepository.save(user);

        if (log.isDebugEnabled()) {
            log.debug(UPDATED_USER + updated);
        }

        return DTOUtils.map(updated, UserDetails.class);
    }

    public void updatePassword(Long id, PasswordForm form) {
        Assert.notNull(id, USER_ID_CAN_NOT_BE_NULL);

        log.debug("update user password by id @" + id);

        User user = userRepository.findOne(id);

        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchedException();
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));

        User saved = userRepository.save(user);

        if (log.isDebugEnabled()) {
            log.debug(UPDATED_USER + saved);
        }
    }

    public void updateProfile(Long id, ProfileForm form) {
        Assert.notNull(id, USER_ID_CAN_NOT_BE_NULL);

        log.debug("update profile for user @" + id + ", profile form@" + form);

        User user = userRepository.findOne(id);

        DTOUtils.mapTo(form, user);

        User saved = userRepository.save(user);

        if (log.isDebugEnabled()) {
            log.debug(UPDATED_USER + saved);
        }
    }

    public UserDetails findUserById(Long id) {
        Assert.notNull(id, USER_ID_CAN_NOT_BE_NULL);

        log.debug("find user by id @" + id);

        User user = userRepository.findOne(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        return DTOUtils.map(user, UserDetails.class);
    }

    public UserDetails findUserByUsername(String username) {
        Assert.notNull(username, USER_ID_CAN_NOT_BE_NULL);

        log.debug("find user by username @" + username);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException(String.format("username %s was not found", username));
        }

        return DTOUtils.map(user, UserDetails.class);
    }

    public void deleteUser(Long id) {
        Assert.notNull(id, USER_ID_CAN_NOT_BE_NULL);

        log.debug("delete user by id @" + id);

        userRepository.delete(id);
    }

}
