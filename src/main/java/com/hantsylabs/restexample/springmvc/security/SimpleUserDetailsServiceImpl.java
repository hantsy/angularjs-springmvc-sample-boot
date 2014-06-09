package com.hantsylabs.restexample.springmvc.security;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hantsylabs.restexample.springmvc.model.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;

@Named
public class SimpleUserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory
			.getLogger(SimpleUserDetailsServiceImpl.class);

	@Inject
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<User> users = userRepository.findByUsername(username);
		log.debug("found users by username @" + username + ", user count @"
				+ users.size());
		if (!users.isEmpty()) {
			return users.get(0);
		}
		
		return null;
	}

}
