package com.mm.homeworks.model.response;

import com.mm.homeworks.model.entity.UserType;

public class UserDTO {
	private String username;
	private UserType userType;
	
	public UserDTO() {
	}
	
	public UserDTO(String username, UserType userType) {
		this.username = username;
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserType() {
		return userType.toString();
	}
	
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
