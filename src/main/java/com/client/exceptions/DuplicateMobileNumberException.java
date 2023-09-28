package com.client.exceptions;

public class DuplicateMobileNumberException extends RuntimeException {

	private static final long serialVersionUID = 8562212575647997358L;

	public DuplicateMobileNumberException(String exception) {
		super(exception);
	}

}