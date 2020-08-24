package com.mm.homeworks.model.response;

import java.util.List;

public class SubjectStudentsDTO {
	private String name;
	private String teacher;
	private List<StudentDTO> students;
	
	public SubjectStudentsDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
}
