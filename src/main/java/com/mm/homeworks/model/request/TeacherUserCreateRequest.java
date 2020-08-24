package com.mm.homeworks.model.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.mm.homeworks.validations.customAnnotation.MatchingFieldsConstraint;

@MatchingFieldsConstraint(fields = {"password", "confirmPassword"})
public class TeacherUserCreateRequest {
	
	@NotEmpty(message = "Username cannot be empty string")
	@Length(min = 3, message = "Username should be more then 2 characters.")
	private String username;

	@Length(min = 8, max = 20,  message = "Password should be between 8 and 20 characters.")
	@NotEmpty(message = "Password cannot be empty string")
	private String password;

	@NotEmpty(message = "ConfirmPassword cannot be empty string")
	private String confirmPassword;

	@Length(min = 2, max = 100, message = "Name should be between 2 and 50 characters.")
	private String fullname;
	
	private String title;

	public TeacherUserCreateRequest() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
