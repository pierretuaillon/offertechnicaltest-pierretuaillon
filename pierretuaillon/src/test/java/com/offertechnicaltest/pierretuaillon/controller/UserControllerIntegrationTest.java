package com.offertechnicaltest.pierretuaillon.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.offertechnicaltest.pierretuaillon.AbstractTest;
import com.offertechnicaltest.pierretuaillon.model.User;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest extends AbstractTest{
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;

	
	@Test
	public void all() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
		User user1 = new User("johnDoe", "French", cal.getTime(), null, null);
		user1.setId(1L);
		String content = this.restTemplate.getForObject("http://localhost:" + this.port + "/users", String.class);
		User[] userList = mapFromJson(content, User[].class);
		assertTrue(userList[0].equals(user1));
		assertTrue(userList.length > 1);
	}
	
	@Test
	public void newUser() throws JsonParseException, JsonMappingException, IOException {
		
		String contentGet = this.restTemplate.getForObject("http://localhost:" + this.port + "/users", String.class);
		User[] userListGetStart = mapFromJson(contentGet, User[].class);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
	  	cal.set(Calendar.MONTH, 12);
	  	cal.set(Calendar.DAY_OF_MONTH, 27);
	    User user = new User("Jellus", "French", cal.getTime(), "0619797031", "M");
		
		HttpEntity<String> request = new HttpEntity<String>(this.mapToJson(user), headers);
		this.restTemplate.postForObject("http://localhost:" + this.port + "/user", request ,String.class);

		contentGet = this.restTemplate.getForObject("http://localhost:" + this.port + "/users", String.class);
		User[] userListGetEnd = mapFromJson(contentGet, User[].class);
		
		//Check if there is a new user
		assertTrue(userListGetStart.length+1 == userListGetEnd.length);
		
		//Get the last id of users
		user.setId(Long.valueOf(userListGetEnd[userListGetEnd.length-1].getId()));
		
		//Check if the new user is the last user in DB
		assertTrue(user.equals(userListGetEnd[userListGetEnd.length-1]));
	}
	
	@Test
	public void deleteUser() throws JsonParseException, JsonMappingException, IOException {
		String contentGet = this.restTemplate.getForObject("http://localhost:" + this.port + "/users", String.class);
		User[] userListGetStart = mapFromJson(contentGet, User[].class);
		
		System.out.println("------------- http://localhost:" + this.port + "/user/" + userListGetStart.length);
		this.restTemplate.delete("http://localhost:" + this.port + "/user/" + userListGetStart.length);
		
		contentGet = this.restTemplate.getForObject("http://localhost:" + this.port + "/users", String.class);
		User[] userListGetEnd = mapFromJson(contentGet, User[].class);
		
		//Check if an user has been deleted
		assertTrue(userListGetStart.length-1 == userListGetEnd.length);
	}
	
	@Test
	public void one() throws JsonParseException, JsonMappingException, IOException {
		String contentGet = this.restTemplate.getForObject("http://localhost:" + this.port + "/user/1", String.class);
		User user = mapFromJson(contentGet, User.class);
		assertTrue(user.getId() == 1);
	}
	
	@Test
	public void replaceUser() throws JsonParseException, JsonMappingException, IOException {		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
	  	cal.set(Calendar.MONTH, 12);
	  	cal.set(Calendar.DAY_OF_MONTH, 27);
	    User user = new User("Jellus", "French", cal.getTime(), "0619797031", "M");
	    
	    HttpEntity<String> request = new HttpEntity<String>(this.mapToJson(user), headers);
		this.restTemplate.put("http://localhost:" + this.port + "/user/1", request, String.class);
		
		String contentGet = this.restTemplate.getForObject("http://localhost:" + this.port + "/user/1", String.class);
		User userPut = mapFromJson(contentGet, User.class);
		
		user.setId(1L);
		
		assertTrue(user.equals(userPut));
	}
}
