package com.offertechnicaltest.pierretuaillon.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

/**
 * Represents an User
 * @author pierretuaillon
 *
 */

@Entity
public class User {
	private @Id @GeneratedValue Long id;
	private String userName;
	private String contryOfResidence;
	private Date birthdate;
	
	//Optional attributes
	private String phoneNumber;
	private String gender;
	
	public User() {}
	
	/**
	 * Construct of new user
	 * 
	 * @param userName
	 * @param contryOfResidence
	 * @param birthdate
	 * @param phoneNumber 
	 * @param gender
	 */
	public User (String userName, String contryOfResidence, Date birthdate, @Nullable String phoneNumber, @Nullable String gender) {
		this.userName = userName;
		this.contryOfResidence = contryOfResidence;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}
	
	
	/**
	 * Get the id of User
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Set the id of User
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Get the UserName of user
	 * @return UserName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Set the UserName of user
	 * @param userName
	 */	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Get the Contry Of Residence of user
	 * @return contryOfResidence
	 */
	public String getContryOfResidence() {
		return contryOfResidence;
	}
	
	/**
	 * Set the Contry Of Residence of user
	 * @param contryOfResidence
	 */
	public void setContryOfResidence(String contryOfResidence) {
		this.contryOfResidence = contryOfResidence;
	}
	
	/**
	 * Get the Birthdate of user
	 * @return birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}
	
	/**
	 * Set the Birthdate of user
	 * @param birthdate
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	/**
	 * Get the Phone Number of user
	 * @return phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Set the Phone Number of user
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Get the Gender of user
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Set the Gender of user
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Compare 2 users 
	 * Birthdate is compare on the format yyyy-MM-dd
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(!(obj instanceof User)) {
			return false;
		} else {
			User user = (User) obj;
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
			return Objects.equals(this.id, user.id)
					&& Objects.equals(this.userName, user.userName)
					&& Objects.equals(this.contryOfResidence, user.contryOfResidence)
					&& Objects.equals(DATE_FORMAT.format(this.birthdate), DATE_FORMAT.format(user.birthdate))
					&& Objects.equals(this.phoneNumber, user.phoneNumber)
					&& Objects.equals(this.gender, user.gender);
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", contryOfResidence=" + contryOfResidence + ", birthdate="
				+ birthdate + ", phoneNumber=" + phoneNumber + ", gender=" + gender + "]";
	}
		
}
