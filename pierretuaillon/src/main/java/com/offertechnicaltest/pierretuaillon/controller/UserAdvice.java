package com.offertechnicaltest.pierretuaillon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.offertechnicaltest.pierretuaillon.exceptions.UserForbiddenException;
import com.offertechnicaltest.pierretuaillon.exceptions.UserNotFoundException;
import com.offertechnicaltest.pierretuaillon.exceptions.UserUnprocessableException;

/**
 * Define an HttpStatus for an Exception
 * @author pierretuaillon
 *
 */

@ControllerAdvice
public class UserAdvice {
  
	/**
	 * When user is not found
	 * 
	 * @param ex
	 * @return 404 
	 */
	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String UserNotFoundHandler(UserNotFoundException ex) {
		return ex.getMessage();
	}
  
	/**
	 * when user is Forbidden
	 * @param ex
	 * @return 403
	 */
	@ResponseBody
	@ExceptionHandler(UserForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String UserForbiddenHandler(UserForbiddenException ex) {
		return ex.getMessage();
	}
	
	/**
	 * When the entity of user is UNPROCESSABLE
	 * @param ex
	 * @return 422
	 */
	@ResponseBody
	@ExceptionHandler(UserUnprocessableException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	String UserUnprocessableException(UserUnprocessableException ex) {
		return ex.getMessage();
	}
	
}