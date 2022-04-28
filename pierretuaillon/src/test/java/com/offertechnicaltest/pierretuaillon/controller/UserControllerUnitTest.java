package com.offertechnicaltest.pierretuaillon.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.offertechnicaltest.pierretuaillon.AbstractTest;
import com.offertechnicaltest.pierretuaillon.model.User;
import com.offertechnicaltest.pierretuaillon.repository.UserRepository;


@WebMvcTest(UserController.class)
//@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest extends AbstractTest{
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void all() throws Exception {
		
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user1 = new User("johnDoe", "French", cal.getTime(), null, null);
		user1.setId(1L);
		User user2 = new User("pierreTuaillon", "French", cal.getTime(), "0619797031", "M");
		user2.setId(2L);
		List<User> users = Arrays.asList(user1, user2);
		
		when(userRepository.findAll()).thenReturn(users);
		
		this.mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"id\":1,\"userName\":\"johnDoe\"")))
			.andExpect(content().string(containsString("\"id\":2,\"userName\":\"pierreTuaillon\"")));
	}
	
	@Test
	public void one() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user1 = new User("johnDoe", "French", cal.getTime(), null, null);
	  	user1.setId(1L);
		Optional<User> user = Optional.ofNullable(user1);
		when(userRepository.findById(any(Long.class))).thenReturn(user);
		
		this.mockMvc.perform(get("/user/1"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"id\":1,\"userName\":\"johnDoe\"")));
	}
	
	@Test
	public void one_404() throws Exception {
		this.mockMvc.perform(get("/user/-1"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void newUser() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("Jellus", "French", cal.getTime(), null, null);
	  	//user.setId(3L);
	  	this.mockMvc.perform(post("/user")		
		  	.content(super.mapToJson(user))
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isCreated());  	
	}
	
	@Test
	public void newUser_403() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("Jellus", "English", cal.getTime(), null, null);
	  	//user.setId(3L);
	  	this.mockMvc.perform(post("/user")		
		  	.content(super.mapToJson(user))
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isForbidden());
	}
	
	@Test
	public void newUser_422_UserName() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("", "French", cal.getTime(), null, null);
	  	this.mockMvc.perform(post("/user")		
		  	.content(super.mapToJson(user))
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void newUser_422_ContryOfResidence() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("Jellus", "", cal.getTime(), null, null);
	  	this.mockMvc.perform(post("/user")		
		  	.content(super.mapToJson(user))
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void newUser_422_Birthdate() throws Exception {
	  	User user = new User("Jellus", "French", null, null, null);
	  	this.mockMvc.perform(post("/user")		
		  	.content(super.mapToJson(user))
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void replaceUser() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("Jellus", "French", cal.getTime(), null, null);
	  	
	  	User user1 = new User("johnDoe", "French", cal.getTime(), null, null);
	  	user1.setId(1L);
		Optional<User> userReturn = Optional.ofNullable(user1);
	  	
	  	when(userRepository.findById(any(Long.class))).thenReturn(userReturn);
	  	when(userRepository.save(any(User.class))).thenReturn(user);
		
	  	
	  	this.mockMvc.perform(put("/user/1")		
		  	.content(super.mapToJson(user))
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.accept(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk());
	}
	
	@Test
	public void replaceUser_404() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("Jellus", "French", cal.getTime(), null, null);
	  	
		this.mockMvc.perform(put("/user/1")		
	  	.content(super.mapToJson(user))
	  	.contentType(MediaType.APPLICATION_JSON)
	  	.accept(MediaType.APPLICATION_JSON))
	  	.andExpect(status().isNotFound());
	}
	
	@Test
	public void replaceUser_422_UserName() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("", "French", cal.getTime(), null, null);
	  	
	  	this.mockMvc.perform(put("/user/1")		
	  		  	.content(super.mapToJson(user))
	  		  	.contentType(MediaType.APPLICATION_JSON)
	  		  	.accept(MediaType.APPLICATION_JSON))
	  		  	.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void replaceUser_422_ContryOfResidence() throws Exception {
		Calendar cal = Calendar.getInstance();
	  	cal.set(Calendar.YEAR, 1995);
	  	cal.set(Calendar.MONTH, 11);
	  	cal.set(Calendar.DAY_OF_MONTH, 30);
	  	User user = new User("Jellus", "", cal.getTime(), null, null);
	  	
	  	this.mockMvc.perform(put("/user/1")		
	  		  	.content(super.mapToJson(user))
	  		  	.contentType(MediaType.APPLICATION_JSON)
	  		  	.accept(MediaType.APPLICATION_JSON))
	  		  	.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void replaceUser_422_Birthdate() throws Exception {
	  	User user = new User("Jellus", "French", null, null, null);
	  	
	  	this.mockMvc.perform(put("/user/1")		
	  		  	.content(super.mapToJson(user))
	  		  	.contentType(MediaType.APPLICATION_JSON)
	  		  	.accept(MediaType.APPLICATION_JSON))
	  		  	.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void deleteUser() throws Exception {
		this.mockMvc.perform(delete("/user/1")
				.contentType(MediaType.APPLICATION_JSON)
	  		  	.accept(MediaType.APPLICATION_JSON))
	  		    .andExpect(status().isOk());
	}
	
	@Test
	public void deleteUser_404() throws Exception {
		doThrow(EmptyResultDataAccessException.class).doNothing().when(userRepository).deleteById(any(Long.class));
		
		this.mockMvc.perform(delete("/user/-1")
				.contentType(MediaType.APPLICATION_JSON)
	  		  	.accept(MediaType.APPLICATION_JSON))
	  		    .andExpect(status().isNotFound());
	}
} 