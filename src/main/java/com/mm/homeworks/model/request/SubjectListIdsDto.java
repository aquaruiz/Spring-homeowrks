package com.mm.homeworks.model.request;

import java.util.List;

public class SubjectListIdsDto {
	List<Long> subjectIds;
	
	public SubjectListIdsDto() {
	}

	public List<Long> getSubjectIds() {
		return subjectIds;
	}
	
	public void setSubjectIds(List<Long> subjectIds) {
		this.subjectIds = subjectIds;
	}
}
