package com.offertechnicaltest.pierretuaillon;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.offertechnicaltest.pierretuaillon.model.User;
import com.offertechnicaltest.pierretuaillon.repository.UserRepository;

/**
 * Load the database whith 2 users
 * 
 * @author pierretuaillon
 * 
 */

@Configuration
public class LoadDatabase {
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	/**
	 * Init the database if repository is empty
	 * 
	 * @param repository
	 * @return a repository of users
	 */
	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		if(repository.count() == 0) {
			Calendar cal = Calendar.getInstance();
		  	cal.set(Calendar.YEAR, 1995);
		  	cal.set(Calendar.MONTH, 11);
		  	cal.set(Calendar.DAY_OF_MONTH, 30);
			  
		  	return args -> {
		  		log.info("Preloading " + repository.save(new User("johnDoe", "French", cal.getTime(), null, null)));
		  		log.info("Preloading " + repository.save(new User("pierreTuaillon", "French", cal.getTime(), "0619797031", "M")));
		  	};
		}else {
			return null;
		}
	}
}
