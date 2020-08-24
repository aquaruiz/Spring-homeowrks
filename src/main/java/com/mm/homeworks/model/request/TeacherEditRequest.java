package com.mm.homeworks.model.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class TeacherEditRequest {
	
	@Length(min = 2,  max = 100, message = "Name should be between 2 and 50 characters.")
	@NotEmpty(message = "Fullname cannot be empty.")
	private String fullname;
	
	private String title;

	public TeacherEditRequest() {
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
