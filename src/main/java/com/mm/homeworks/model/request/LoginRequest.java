package com.mm.homeworks.model.request;

import javax.validation.constraints.NotBlank;

import com.mm.homeworks.constants.ErrorMessages;

public class LoginRequest {
	@NotBlank(message = ErrorMessages.BLANK_USERNAME)
	private String username;

	@NotBlank(message = ErrorMessages.BLANK_PASSWORD)
	private String password;
	
	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
}
