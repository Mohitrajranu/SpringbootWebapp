package com.restfulservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

// TODO: Auto-generated Javadoc
/**
 * The Class FreeTrialUser.
 * @author Mohit Raj
 */
@Entity
@Table(name="user_product_assignment")
public class FreeTrialUser {

	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	 /** The username. */
 	@Column(name="USER_NAME")
	 private String username;
	 
 	/** The product type. */
 	@Column(name="PRODUCT_TYPE")
	 private String productType;
	 
 	/** The expireday. */
 	@Temporal(TemporalType.DATE)
	 @Column(name="EXPIRE_PERIOD")
	 private Date expireday;
	 
 	/** The joined date. */
 	@Temporal(TemporalType.DATE)
	 @Column(name="CREATION_DATE")
	 private Date joinedDate;
	 
 	/** The freetrial. */
 	@Column(name="FREE_TRIAL")
	  private int freetrial; 
	 
 	/** The expire flag. */
 	@Column(name="EXPIRED_FLAG")
	  private int expireFlag;
	
	 /**
 	 * Instantiates a new free trial user.
 	 */
 	public FreeTrialUser() {}

	
	/**
	 * Instantiates a new free trial user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param productType the product type
	 * @param expireday the expireday
	 * @param joinedDate the joined date
	 * @param freetrial the freetrial
	 * @param expireFlag the expire flag
	 */
	public FreeTrialUser(int id, String username, String productType, Date expireday, Date joinedDate, int freetrial,
			int expireFlag) {
		this.id = id;
		this.username = username;
		this.productType = productType;
		this.expireday = expireday;
		this.joinedDate = joinedDate;
		this.freetrial = freetrial;
		this.expireFlag = expireFlag;
	}


	/**
	 * Instantiates a new free trial user.
	 *
	 * @param username the username
	 * @param productType the product type
	 * @param expireday the expireday
	 * @param joinedDate the joined date
	 * @param freetrial the freetrial
	 * @param expireFlag the expire flag
	 */
	public FreeTrialUser(String username, String productType, Date expireday, Date joinedDate, int freetrial,
			int expireFlag) {
		this.username = username;
		this.productType = productType;
		this.expireday = expireday;
		this.joinedDate = joinedDate;
		this.freetrial = freetrial;
		this.expireFlag = expireFlag;
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
	 * Gets the product type.
	 *
	 * @return the product type
	 */
	public String getProductType() {
		return productType;
	}


	/**
	 * Sets the product type.
	 *
	 * @param productType the new product type
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}


	/**
	 * Gets the expireday.
	 *
	 * @return the expireday
	 */
	public Date getExpireday() {
		return expireday;
	}

	/**
	 * Sets the expireday.
	 *
	 * @param expireday the new expireday
	 */
	public void setExpireday(Date expireday) {
		this.expireday = expireday;
	}

	/**
	 * Gets the joined date.
	 *
	 * @return the joined date
	 */
	public Date getJoinedDate() {
		return joinedDate;
	}

	/**
	 * Sets the joined date.
	 *
	 * @param joinedDate the new joined date
	 */
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	/**
	 * Gets the freetrial.
	 *
	 * @return the freetrial
	 */
	public int getFreetrial() {
		return freetrial;
	}

	/**
	 * Sets the freetrial.
	 *
	 * @param freetrial the new freetrial
	 */
	public void setFreetrial(int freetrial) {
		this.freetrial = freetrial;
	}

	/**
	 * Gets the expire flag.
	 *
	 * @return the expire flag
	 */
	public int getExpireFlag() {
		return expireFlag;
	}

	/**
	 * Sets the expire flag.
	 *
	 * @param expireFlag the new expire flag
	 */
	public void setExpireFlag(int expireFlag) {
		this.expireFlag = expireFlag;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"FreeTrialUser [id=%s, username=%s, productType=%s, expireday=%s, joinedDate=%s, freetrial=%s, expireFlag=%s]",
				id, username, productType, expireday, joinedDate, freetrial, expireFlag);
	}

}
