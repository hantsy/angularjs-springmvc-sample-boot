package com.hantsylabs.restexample.springmvc.security;

import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SimpleUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SimpleUserDetailsServiceImpl.class);

    private UserRepository userRepository;

    public SimpleUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found:" + username);
        }

        log.debug("found by username @" + username);

        return user;

    }

}
