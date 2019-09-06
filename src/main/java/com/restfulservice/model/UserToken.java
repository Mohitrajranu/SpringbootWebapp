package com.restfulservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
*/
@Entity
@Table(name = "user_token")
public class UserToken {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	//@Email(message = "Please provide a valid e-mail")
	//@NotEmpty(message = "Please provide an e-mail")
	private String email;

	@Column(name = "currentstatus")
	private int currentstatus;
	@Column(name = "reset_token")
	private String resetToken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	


	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * @return the currentstatus
	 */
	public int getCurrentstatus() {
		return currentstatus;
	}

	/**
	 * @param currentstatus the currentstatus to set
	 */
	public void setCurrentstatus(int currentstatus) {
		this.currentstatus = currentstatus;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
}
