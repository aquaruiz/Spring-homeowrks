package com.mm.homeworks.model.response;

import javax.validation.constraints.NotEmpty;

public class SubjectDTO {
	@NotEmpty(message = "Subject name cannot be empty")
	private String name;
	
	public SubjectDTO() {
	}
	
	public SubjectDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
