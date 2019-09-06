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

@Entity
@Table(name="user_product_assignment")
public class FreeTrialUser {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	 @Column(name="USER_NAME")
	 private String username;
	 @Column(name="PRODUCT_TYPE")
	 private String productType;
	 @Temporal(TemporalType.DATE)
	 @Column(name="EXPIRE_PERIOD")
	 private Date expireday;
	 @Temporal(TemporalType.DATE)
	 @Column(name="CREATION_DATE")
	 private Date joinedDate;
	 @Column(name="FREE_TRIAL")
	  private int freetrial; 
	 @Column(name="EXPIRED_FLAG")
	  private int expireFlag;
	
	 public FreeTrialUser() {}

	
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


	public FreeTrialUser(String username, String productType, Date expireday, Date joinedDate, int freetrial,
			int expireFlag) {
		this.username = username;
		this.productType = productType;
		this.expireday = expireday;
		this.joinedDate = joinedDate;
		this.freetrial = freetrial;
		this.expireFlag = expireFlag;
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
	

	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public Date getExpireday() {
		return expireday;
	}

	public void setExpireday(Date expireday) {
		this.expireday = expireday;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public int getFreetrial() {
		return freetrial;
	}

	public void setFreetrial(int freetrial) {
		this.freetrial = freetrial;
	}

	public int getExpireFlag() {
		return expireFlag;
	}

	public void setExpireFlag(int expireFlag) {
		this.expireFlag = expireFlag;
	}


	@Override
	public String toString() {
		return String.format(
				"FreeTrialUser [id=%s, username=%s, productType=%s, expireday=%s, joinedDate=%s, freetrial=%s, expireFlag=%s]",
				id, username, productType, expireday, joinedDate, freetrial, expireFlag);
	}

}
