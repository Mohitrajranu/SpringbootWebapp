package com.restfulservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_role_assignment")
public class User {
	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="USER_ROLE_PK_ID")
	    private int id;
	  
	 /* USER_ROLE_PK_ID | int(10) unsigned    | NO   | PRI | NULL    | auto_increment |
	  | USER_NAME       | varchar(50)         | NO   |     | NULL    |                |
	  | ROLE_ID         | int(10) unsigned    | NO   |     | NULL    |                |
	  | IS_ACTIVE       | tinyint(1) unsigned 
	 */
	  @Column(name="USER_NAME")
	  private String username;
	  @Column(name="ROLE_ID")
	  private int roleid;
	  @Column(name="IS_ACTIVE")
	  private int isactive;
	  
	 	  
	  public User(){}


	public User(int id, String username, int roleid, int isactive) {
		this.id = id;
		this.username = username;
		this.roleid = roleid;
		this.isactive = isactive;
	}


	public User(String username, int roleid, int isactive) {
		this.username = username;
		this.roleid = roleid;
		this.isactive = isactive;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public int getRoleid() {
		return roleid;
	}


	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}


	public int getIsactive() {
		return isactive;
	}


	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}


	@Override
	public String toString() {
		return String.format("User [id=%s, username=%s, roleid=%s, isactive=%s]", id, username, roleid, isactive);
	}

	
	  

		  
}
