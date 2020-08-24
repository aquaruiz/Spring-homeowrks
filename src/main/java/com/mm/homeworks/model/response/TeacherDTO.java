package com.mm.homeworks.model.response;

import java.util.ArrayList;
import java.util.List;

import com.mm.homeworks.model.entity.Title;

public class TeacherDTO {
	private Title title;
	private String fullname;
	private List<SubjectDTO> subjects;
	
	public TeacherDTO() {
		this.subjects = new ArrayList<SubjectDTO>();
	}

	public TeacherDTO(Title title, String fullname) {
		this.title = title;
		this.fullname = fullname;
		this.subjects = new ArrayList<SubjectDTO>();
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void addSubject(SubjectDTO subject) {
		if (subject == null) {
			return;
		}
		
		this.subjects.add(subject);
	}
	
	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}
}	
