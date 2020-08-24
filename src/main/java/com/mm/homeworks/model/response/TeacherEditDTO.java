package com.mm.homeworks.model.response;

public class TeacherEditDTO {
	private String id;
	private String fullname;
	private String title;
	
	public TeacherEditDTO() {
	}
	
	public TeacherEditDTO(String id, String fullname, String title) {
		this.id = id;
		this.fullname = fullname;
		this.title = title;
	}

	public String getId() {
		return id;
	}


	public String getFullname() {
		return fullname;
	}


	public String getTitle() {
		return title;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
