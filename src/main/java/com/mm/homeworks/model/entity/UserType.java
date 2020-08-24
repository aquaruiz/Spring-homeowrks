package com.mm.homeworks.model.entity;

public enum UserType {
	ADMIN, STUDENT, TEACHER;
	
	public static final UserType[] values = values();
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
