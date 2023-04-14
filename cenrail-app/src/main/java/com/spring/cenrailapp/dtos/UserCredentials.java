package com.spring.cenrailapp.dtos;


public class UserCredentials {
	
	private String userNameInput;
	
	private String passwordInput;

	private boolean userAuthenticated;

	public String getUserNameInput() {
		return userNameInput;
	}

	public void setUserNameInput(String userNameInput) {
		this.userNameInput = userNameInput;
	}

	public String getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(String passwordInput) {
		this.passwordInput = passwordInput;
	}
	
	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}

	public void setUserAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}
	
	public UserCredentials () {
		
	}
}
