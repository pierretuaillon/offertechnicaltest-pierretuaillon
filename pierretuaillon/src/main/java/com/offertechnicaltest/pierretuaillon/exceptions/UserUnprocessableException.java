package com.offertechnicaltest.pierretuaillon.exceptions;

/**
 * Define the message of UserUnprocessableException
 * 
 * @author pierretuaillon
 *
 */
public class UserUnprocessableException extends RuntimeException {

	
	/**
	 * Custom message with the entity
	 * @param entity
	 */
	public UserUnprocessableException(String entity) {
		super("the entity " + entity + " can't be blank or empty");
	}
}
