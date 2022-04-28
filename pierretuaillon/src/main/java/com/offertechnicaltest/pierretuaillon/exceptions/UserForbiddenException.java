package com.offertechnicaltest.pierretuaillon.exceptions;

import com.offertechnicaltest.pierretuaillon.model.User;

/**
 * Define the message of UserForbiddenException
 * 
 * @author pierretuaillon
 *
 */

public class UserForbiddenException extends RuntimeException {

	/**
	 * Custom message with the user
	 * @param user
	 */
	
	public UserForbiddenException(User user){
		super("the user is unauthorized " + user);
	}
}
