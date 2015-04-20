package com.hantsylabs.restexample.springmvc.api.user;

import com.hantsylabs.restexample.springmvc.model.ResponseMessage;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hantsylabs.restexample.springmvc.Constants;
import com.hantsylabs.restexample.springmvc.model.UserDetails;
import com.hantsylabs.restexample.springmvc.model.UserForm;
import com.hantsylabs.restexample.springmvc.service.UserService;
import com.hantsylabs.restexample.springmvc.exception.InvalidRequestException;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = Constants.URI_API_MGT + Constants.URI_USERS)
public class UserMgtController {

    private static final Logger log = LoggerFactory
        .getLogger(UserMgtController.class);

    @Inject
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Page<UserDetails> allUsers(
        @RequestParam(required = false, value = "q") String q,
        @RequestParam(required = false, value = "role") String role,
        @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable page) {
        if (log.isDebugEnabled()) {
            log.debug("fetch all users...@" + q + ", role @" + role);
        }

        Page<UserDetails> users = userService.findAll(q, role, page);

        if (log.isDebugEnabled()) {
            log.debug("count of users @" + users.getTotalElements());
        }

        return users;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseMessage> saveUser(@RequestBody @Valid UserForm form, BindingResult errors) {
        if (log.isDebugEnabled()) {
            log.debug("save user data @" + form);
        }

        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }

        userService.saveUser(form);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (log.isDebugEnabled()) {
            log.debug("delete user data @" + id);
        }

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
