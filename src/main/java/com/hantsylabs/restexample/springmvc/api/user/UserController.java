package com.hantsylabs.restexample.springmvc.api.user;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hantsylabs.restexample.springmvc.Constants;
import com.hantsylabs.restexample.springmvc.exception.ResourceNotFoundException;
import com.hantsylabs.restexample.springmvc.model.UserDetails;
import com.hantsylabs.restexample.springmvc.service.UserService;

@RestController
@RequestMapping(value = Constants.URI_API_PUBLIC + Constants.URI_USERS)
public class UserController {

    private static final Logger log = LoggerFactory
        .getLogger(UserController.class);

    @Inject
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserDetails> getUser(@PathVariable("id") Long id) {
        if (log.isDebugEnabled()) {
            log.debug("get user data @" + id);
        }

        UserDetails user = userService.findUserById(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
