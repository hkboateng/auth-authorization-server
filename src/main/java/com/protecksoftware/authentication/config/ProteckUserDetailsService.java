package com.protecksoftware.authentication.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.protecksoftware.authentication.auth.resposity.LoginUserRepository;
import com.protecksoftware.authentication.business.entity.LoginUser;
@Service("proteckUserDetailsService")
public class ProteckUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(ProteckUserDetailsService.class);
	@Autowired
	private LoginUserRepository loginUserRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser loginUser = loginUserRepository.findByUsername(username);
		if(loginUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new User(loginUser.getUsername(),"",new ArrayList<>());
	}

}
