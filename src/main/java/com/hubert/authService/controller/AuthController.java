package com.hubert.authService.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hubert.authService.auth.response.AuthResponse;

@RequestMapping(value="/auth")
@RestController
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(value="/login",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public ResponseEntity<AuthResponse> login(String username, String password) {
		AuthResponse auth = new AuthResponse();
		auth.setStatus(true);
		auth.setStatusMessage("thanks");
		return new ResponseEntity<AuthResponse>(auth,HttpStatus.OK);
	}
	
	@RequestMapping(value="/createAccount.process",method=RequestMethod.POST)
	public void createAcount(String createAccount,HttpServletRequest request) {
		logger.info("Create Account process has being initiated");
	}
}
