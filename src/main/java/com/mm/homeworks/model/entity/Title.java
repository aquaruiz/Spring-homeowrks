package com.mm.homeworks.model.entity;

import java.util.Arrays;

public enum Title {
	MR, MRS, MISS, DOCTOR, PROFESSOR, NONE;
	
	public static final Title[] values = values();

	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().toLowerCase().substring(1);
	}
	
	public static boolean isAvailable(String titleString) {
		return Arrays.stream(values).anyMatch((title) -> title.name().equals(titleString));
	}
}
