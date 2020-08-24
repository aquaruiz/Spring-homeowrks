package com.mm.homeworks.constants;

final public class ErrorMessages {
	public static final String  FIELDS_ARE_NOT_MATCHING = "Fileds do not match";

	private static final String USERNAME_ALREADY_TAKEN = "User with username '%s' already exists.";
	private static final String ENTITY_WITH_ID_NOT_EXISTS = "%s with id '%s' does not exist.";

	public static final String BLANK_USERNAME = "Username must not be blank.";
	public static final String BLANK_PASSWORD = "Password must not be blank.";
	
	public static String getUsernameAlreadyTaken(String username) {
		return String.format(USERNAME_ALREADY_TAKEN, username);
	}
	
	public static String getEntityWithIdNotExists(String entity, String id) {
		return  String.format(ENTITY_WITH_ID_NOT_EXISTS, entity, id);
	}
}
