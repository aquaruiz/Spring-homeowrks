package com.mm.homeworks.customExceptions;

public class DuplicateEntityException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEntityException(String errorMessage) {
		super(errorMessage);
	}

}
