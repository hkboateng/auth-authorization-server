package com.protecksoftware.authentication.auth.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.protecksoftware.authentication.request.LoginRequest;

@Service("authenticationService")
public interface AuthenticationService {
	void authenticateLogin(LoginRequest request);
}
