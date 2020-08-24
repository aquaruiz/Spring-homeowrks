package com.mm.homeworks.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.mm.homeworks.validations.customAnnotation.MatchingFieldsConstraint;

@MatchingFieldsConstraint(fields = {"password", "confirmPassword"})
public class StudentUserCreateRequest {
	
	@NotEmpty(message = "Username cannot be empty string")
	@Length(min = 3, message = "Username should be more then 2 characters.")
	private String username;

	@Length(min = 8, max = 20,  message = "Password should be between 8 and 20 characters.")
	@NotEmpty(message = "Password cannot be empty string")
	private String password;

	@NotEmpty(message = "ConfirmPassword cannot be empty string")
	private String confirmPassword;

	@Length( min = 2, max = 50, message = "firstName should be between 2 and 50 characters.")
	private String firstName;
	
	@Length(min = 2, max = 50, message = "lastName should be between 2 and 50 characters.")
	private String lastName;
	
	@Positive(message = "Age cannot be empty")
	@Min(value = 16, message = "You cannot be younger then 16.")
	@Max(value = 40, message = "You cannot be older then 40.")
	private int age;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
