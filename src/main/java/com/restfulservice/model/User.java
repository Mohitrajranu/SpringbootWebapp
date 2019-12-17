package com.restfulservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 *  @author Mohit Raj
 */
@Entity
@Table(name="user_role_assignment")
public class User {
	  
  	/** The id. */
  	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="USER_ROLE_PK_ID")
	    private int id;
	  
	 /** The username. */
 	/* USER_ROLE_PK_ID | int(10) unsigned    | NO   | PRI | NULL    | auto_increment |
	  | USER_NAME       | varchar(50)         | NO   |     | NULL    |                |
	  | ROLE_ID         | int(10) unsigned    | NO   |     | NULL    |                |
	  | IS_ACTIVE       | tinyint(1) unsigned 
	 */
	  @Column(name="USER_NAME")
	  private String username;
	  
  	/** The roleid. */
  	@Column(name="ROLE_ID")
	  private int roleid;
	  
  	/** The isactive. */
  	@Column(name="IS_ACTIVE")
	  private int isactive;
	  
	 	  
	  /**
  	 * Instantiates a new user.
  	 */
  	public User(){}


	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param roleid the roleid
	 * @param isactive the isactive
	 */
	public User(int id, String username, int roleid, int isactive) {
		this.id = id;
		this.username = username;
		this.roleid = roleid;
		this.isactive = isactive;
	}


	/**
	 * Instantiates a new user.
	 *
	 * @param username the username
	 * @param roleid the roleid
	 * @param isactive the isactive
	 */
	public User(String username, int roleid, int isactive) {
		this.username = username;
		this.roleid = roleid;
		this.isactive = isactive;
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * Gets the roleid.
	 *
	 * @return the roleid
	 */
	public int getRoleid() {
		return roleid;
	}


	/**
	 * Sets the roleid.
	 *
	 * @param roleid the new roleid
	 */
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}


	/**
	 * Gets the isactive.
	 *
	 * @return the isactive
	 */
	public int getIsactive() {
		return isactive;
	}


	/**
	 * Sets the isactive.
	 *
	 * @param isactive the new isactive
	 */
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("User [id=%s, username=%s, roleid=%s, isactive=%s]", id, username, roleid, isactive);
	}

	
	  

		  
}
