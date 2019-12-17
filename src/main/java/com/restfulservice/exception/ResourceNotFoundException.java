package com.restfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceNotFoundException.
 * @author Mohit Raj
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	/** The resource name. */
	private String resourceName;
	
	/** The field name. */
	private String fieldName;
	
	/** The field value. */
	private Object fieldValue;

	/**
	 * Instantiates a new resource not found exception.
	 *
	 * @param resourceName the resource name
	 * @param fieldName the field name
	 * @param fieldValue the field value
	 */
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	/**
	 * Gets the resource name.
	 *
	 * @return the resource name
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Gets the field value.
	 *
	 * @return the field value
	 */
	public Object getFieldValue() {
		return fieldValue;
	}
}