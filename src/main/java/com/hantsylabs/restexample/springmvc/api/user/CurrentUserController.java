package com.hantsylabs.restexample.springmvc.api.user;

import com.hantsylabs.restexample.springmvc.Constants;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.exception.InvalidRequestException;
import com.hantsylabs.restexample.springmvc.model.PasswordForm;
import com.hantsylabs.restexample.springmvc.model.ProfileForm;
import com.hantsylabs.restexample.springmvc.model.UserDetails;
import com.hantsylabs.restexample.springmvc.security.CurrentUser;
import com.hantsylabs.restexample.springmvc.service.UserService;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_SELF)
public class CurrentUserController {

    private static final Logger log = LoggerFactory
            .getLogger(CurrentUserController.class);

    @Inject
    private UserService userService;

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    @ResponseBody
    @GetMapping()
    public UserDetails currentUser(@CurrentUser User user) {

        log.debug("get current user info");

        UserDetails details = userService.findUserById(user.getId());

        log.debug("current user value @" + details);

        return details;
    }

//    @RequestMapping(value = "/password", method = RequestMethod.PUT)
//    @ResponseBody
    @PutMapping(value="/password")
    public ResponseEntity<Void> changePassword(
            @CurrentUser User user,
            @RequestBody @Valid PasswordForm fm,
            BindingResult result) {

        log.debug("change password of user@" + fm);
        
        if (result.hasErrors()) {
            throw new InvalidRequestException(result);
        }

        userService.updatePassword(user.getId(), fm);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
//    @ResponseBody
    @PutMapping(value="/profile")
    public ResponseEntity<Void> updateProfile(
            @CurrentUser User user,
            @RequestBody @Valid ProfileForm fm,
            BindingResult result) {

        log.debug("update user profile data @" + fm);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result);
        }

        userService.updateProfile(user.getId(), fm);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
