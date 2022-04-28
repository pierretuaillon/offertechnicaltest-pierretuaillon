package com.offertechnicaltest.pierretuaillon.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.offertechnicaltest.pierretuaillon.aspect.Timed;
import com.offertechnicaltest.pierretuaillon.exceptions.UserForbiddenException;
import com.offertechnicaltest.pierretuaillon.exceptions.UserNotFoundException;
import com.offertechnicaltest.pierretuaillon.exceptions.UserUnprocessableException;
import com.offertechnicaltest.pierretuaillon.model.User;
import com.offertechnicaltest.pierretuaillon.repository.UserRepository;


/**
 * 
 * @author pierretuaillon
 *
 */

@RestController
public class UserController {

	private final UserRepository repository;

	public UserController(UserRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Get all user in UserRepository
	 * @return List of user
	 */
	
	@Timed
	@GetMapping("/users")
	List<User> all() {
		return this.repository.findAll();
	}
	
	/**
	 * Create a new User
	 * @param newUser
	 * @throws UserUnprocessableException 422 when getUserName or getContryOfResidence or getBirthdate are empty ou null
	 * @throws UserForbiddenException 403 when getContryOfResidence is not French
	 * @return newUser
	 */
	
	@Timed
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/user")
	User newUser(@RequestBody User newUser) {
		if(newUser.getUserName().isBlank() || newUser.getUserName()== null) {
			throw new UserUnprocessableException("Username");
		}
		
		if(newUser.getContryOfResidence().isBlank() || newUser.getContryOfResidence() == null) {
			throw new UserUnprocessableException("ContryOfResidence");
		}
		
		if (newUser.getBirthdate() == null) {
			throw new UserUnprocessableException("Birthdate");
		}
		
		if(newUser.getContryOfResidence().equalsIgnoreCase("French")) {
			return repository.save(newUser);
		} else {
			throw new UserForbiddenException(newUser);
		}
	}
	
	/**
	 * Uptade user whith the id in parameter by newUser
	 * @param newUser is the user who will replace the old
	 * @param id of user to update
	 * @throws UserUnprocessableException 422 when getUserName or getContryOfResidence or getBirthdate are empty ou null
	 * @throws UserNotFoundException 404 when id is not found
	 * @return User with new data
	 */
	@PutMapping("/user/{id}")
	User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
	    
		if(newUser.getUserName().isBlank() || newUser.getUserName()== null) {
			throw new UserUnprocessableException("Username");
		}
		
		if(newUser.getContryOfResidence().isBlank() || newUser.getContryOfResidence() == null) {
			throw new UserUnprocessableException("ContryOfResidence");
		}
		
		if (newUser.getBirthdate() == null) {
			throw new UserUnprocessableException("Birthdate");
		}
		
		return repository.findById(id).map(user -> {
			user.setUserName(newUser.getUserName());
			user.setBirthdate(newUser.getBirthdate());
			user.setContryOfResidence(newUser.getContryOfResidence());
			user.setPhoneNumber(newUser.getPhoneNumber());
			user.setGender(newUser.getGender());
	        return repository.save(user);
	    }).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	/**
	 * Get one user define by this id
	 * @param id 
	 * @throws UserNotFoundException 404 when id is not found
	 * @return User
	 */
	@Timed
	@GetMapping("/user/{id}")
	User one(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	/**
	 * Delete an user
	 * @param id of user to delete
	 * @throws UserNotFoundException 404 when the user is not found
	 */
	@Timed
	@DeleteMapping("/user/{id}")
	void deleteUser(@PathVariable Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException exc) {
			throw new UserNotFoundException(id);
		}
	}
}