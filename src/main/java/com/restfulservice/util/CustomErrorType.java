package com.restfulservice.util;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomErrorType.
 */
public class CustomErrorType {

	/** The error message. */
	private String errorMessage;
	 
    /**
     * Instantiates a new custom error type.
     *
     * @param errorMessage the error message
     */
    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }
 
    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
 
}
