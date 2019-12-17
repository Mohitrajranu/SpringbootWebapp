package com.restfulservice.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class ApiResponse.
 * @author Mohit Raj
 */
public class ApiResponse {

	/** The success. */
	private Boolean success;
	
	/** The message. */
	private String message;

	/**
	 * Instantiates a new api response.
	 *
	 * @param success the success
	 * @param message the message
	 */
	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * Gets the success.
	 *
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * Sets the success.
	 *
	 * @param success the new success
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
