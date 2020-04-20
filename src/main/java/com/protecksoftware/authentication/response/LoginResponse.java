package com.protecksoftware.authentication.response;

public class LoginResponse {

	private Boolean responseStatus;
	private String responseMessage;
	private String token;
	
	public Boolean getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(Boolean responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
