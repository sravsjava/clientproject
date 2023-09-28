package com.client.exceptions;

public class DuplicateIDException extends RuntimeException {

	private static final long serialVersionUID = 8562212575647997358L;

	public DuplicateIDException(String exception) {
		super(exception);
	}

}