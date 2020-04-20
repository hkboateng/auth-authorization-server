package com.protecksoftware.authentication.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.protecksoftware.authentication.auth.resposity.LoginUserRepository;
import com.protecksoftware.authentication.business.entity.LoginUser;
import com.protecksoftware.authentication.business.entity.Permission;
import com.protecksoftware.authentication.request.LoginRequest;

@Service("proteckAuthenticationProvider")
public class ProteckAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(ProteckAuthenticationProvider.class);
	
	@Autowired
	private LoginUserRepository loginUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public Authentication authenticate(Authentication authenticate) throws AuthenticationException {
		LoginRequest loginRequest = new LoginRequest(authenticate.getName().trim(),authenticate.getCredentials().toString().trim());
		LoginUser loginUser = loginUserRepository.findByUsername(loginRequest.getUsername());
		if(loginUser != null) {
			boolean matches = passwordEncoder.matches(loginRequest.getPassword(), loginUser.getPassword());
			if(matches && loginUser.getAccountExpired()) {
				throw new CredentialsExpiredException("");
			}else if(matches && loginUser.getAccountLocked()) {
				throw new DisabledException("");
			}else if(matches && !loginUser.getAccountLocked()&& !loginUser.getAccountExpired()) {
				return new UsernamePasswordAuthenticationToken("","",null);
			}else if(!matches) {
				throw new BadCredentialsException("");
			}
		}
		return new UsernamePasswordAuthenticationToken("","",null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	private List<Permission> getAllUserPermission(String[] permissionList){
		List<Permission> permissions = null;
		if(permissionList != null){
			permissions = new ArrayList<Permission>();
			for(String permission: permissionList){
				Permission p = new Permission();
				p.setPermission("ROLE_"+permission);
				permissions.add(p);
			}
		}
		return permissions;
	}
	public List<String> getUserPermissions(String username){
		logger.info("Retreiving user's permissions.....");
		List<String> permissionList = null;
		logger.info("Retreiving user's permission completed.....");
		return permissionList;
	}
}
