package com.mm.homeworks.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

public class StudentEditRequest {
	
	@Length(min = 2, max = 50, message = "firstName should be between 2 and 50 characters.")
	private String firstName;
	
	@Length(min = 2, max = 50, message = "lastName should be between 2 and 50 characters.")
	private String lastName;
	
	@Positive(message = "Age cannot be empty")
	@Min(value = 16, message = "You cannot be younger then 16.")
	@Max(value = 40, message = "You cannot be older then 40.")
	private int age;

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
}
