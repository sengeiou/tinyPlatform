package com.wanliang.micro.web.exception;

import com.wanliang.micro.web.controller.exception.ErrorMessage;
import com.wanliang.micro.web.controller.exception.ErrorMessageCollection;

public class ValidationException extends RuntimeException {

	private ErrorMessageCollection errorMessageCollection = new ErrorMessageCollection();

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException() {
		super();
	}

	public ErrorMessageCollection getErrorMessageCollection() {
		return errorMessageCollection;
	}

	public void addErrorMessages(String message) {
		errorMessageCollection.addErrorMessages("Error", "", message, "Severe");
	}

	public void addErrorMessages(String attributeName, String message) {
		errorMessageCollection.addErrorMessages("Error", attributeName,
				message, "Severe");
	}

	public void addErrorMessages(String type, String attributeName,
			String message, String severity) {
		errorMessageCollection.addErrorMessages(type, attributeName, message,
				severity);
	}

	public ErrorMessage getErrorMessage() {
		return errorMessageCollection.getErrorMessages().get(0);
	}

	public void addErrorMessages(ErrorMessage errorMessage) {
		errorMessageCollection.addErrorMessages(errorMessage.getType(),
				errorMessage.getAttributeName(), errorMessage.getMessage(),
				errorMessage.getSeverity());

	}

	public boolean isErrorExist() {
		// TODO Auto-generated method stub

		if (errorMessageCollection.getErrorMessages().size() > 0) {
			return true;
		}
		return false;
	}
}
