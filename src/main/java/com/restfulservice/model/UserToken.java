package com.restfulservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class UserToken.
 *  @author Mohit Raj
 */
/*import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
*/
@Entity
@Table(name = "user_token")
public class UserToken {

	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	/** The email. */
	@Column(name = "email")
	//@Email(message = "Please provide a valid e-mail")
	//@NotEmpty(message = "Please provide an e-mail")
	private String email;

	/** The currentstatus. */
	@Column(name = "currentstatus")
	private int currentstatus;
	
	/** The reset token. */
	@Column(name = "reset_token")
	private String resetToken;

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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * Gets the currentstatus.
	 *
	 * @return the currentstatus
	 */
	public int getCurrentstatus() {
		return currentstatus;
	}

	/**
	 * Sets the currentstatus.
	 *
	 * @param currentstatus the currentstatus to set
	 */
	public void setCurrentstatus(int currentstatus) {
		this.currentstatus = currentstatus;
	}

	/**
	 * Gets the reset token.
	 *
	 * @return the reset token
	 */
	public String getResetToken() {
		return resetToken;
	}

	/**
	 * Sets the reset token.
	 *
	 * @param resetToken the new reset token
	 */
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	
	
}
