package com.hantsylabs.restexample.springmvc.api.user;

import com.hantsylabs.restexample.springmvc.Constants;
import com.hantsylabs.restexample.springmvc.exception.InvalidRequestException;
import com.hantsylabs.restexample.springmvc.model.UserDetails;
import com.hantsylabs.restexample.springmvc.model.UserForm;
import com.hantsylabs.restexample.springmvc.service.UserService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_USERS)
public class UserController {

    private static final Logger log = LoggerFactory
            .getLogger(UserController.class);

    @Inject
    private UserService userService;

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetails> getUser(@PathVariable("id") Long id) {

        log.debug("get user data @" + id);

        UserDetails user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    @ResponseBody
     @GetMapping()
    public Page<UserDetails> allUsers(
            @RequestParam(required = false, value = "q") String q,
            @RequestParam(required = false, value = "role") String role,
            @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable page) {

        log.debug("fetch all users...@" + q + ", role @" + role);

        Page<UserDetails> users = userService.findAll(q, role, page);

        log.debug("count of fetched users @" + users.getTotalElements());

        return users;
    }

//    @RequestMapping(value = "", method = RequestMethod.POST)
//    @ResponseBody
     @PostMapping()
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserForm form,
            BindingResult errors,
            HttpServletRequest req) {

        log.debug("save user data @" + form);

        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }

        UserDetails userDetails = userService.saveUser(form);

        log.debug("created user@" + userDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                ServletUriComponentsBuilder
                .fromContextPath(req)
                .path(Constants.URI_API_PREFIX + Constants.URI_USERS + "/{id}")
                .buildAndExpand(userDetails.getId()).toUri()
        );

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

//    @RequestMapping(value = "/username-check", method = RequestMethod.GET)
//    @ResponseBody
     @GetMapping(value = "/username-check")
    public ResponseEntity<Boolean> checkUsername(@RequestParam("username") String username) {

        log.debug("check username existance by username @" + username);

        UserDetails userDetails = userService.findUserByUsername(username);
        boolean found = (userDetails != null);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
     @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

        log.debug("delete user by id @" + id);

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
