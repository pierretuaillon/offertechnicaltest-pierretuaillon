package com.offertechnicaltest.pierretuaillon.exceptions;

/**
 * Define the message of UserNotFoundException
 * 
 * @author pierretuaillon
 *
 */

public class UserNotFoundException extends RuntimeException {

	/**
	 * Custom message with the id
	 * @param id
	 */
	public UserNotFoundException(Long id) {
		super("Could not find user " + id);
	}
}
