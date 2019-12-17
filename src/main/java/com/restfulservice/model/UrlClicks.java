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
 * The Class UrlClicks.
 *  @author Mohit Raj
 */
@Entity
@Table(name="url_views")
public class UrlClicks {

	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	/** The email. */
	@Column(name="email")
	private String email;
	
	/** The click count. */
	@Column(name="click_count")
	private Integer clickCount;
	
	/** The templatename. */
	@Column(name="templatename")
	private String templatename;
	
	/** The url. */
	@Column(name="url")
	private String url;
	
	/** The create date. */
	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createDate;
	
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
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the click count.
	 *
	 * @return the clickCount
	 */
	public Integer getClickCount() {
		return clickCount;
	}
	
	/**
	 * Sets the click count.
	 *
	 * @param clickCount the clickCount to set
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	
	/**
	 * Gets the templatename.
	 *
	 * @return the templatename
	 */
	public String getTemplatename() {
		return templatename;
	}
	
	/**
	 * Sets the templatename.
	 *
	 * @param templatename the templatename to set
	 */
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the url.
	 *
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
