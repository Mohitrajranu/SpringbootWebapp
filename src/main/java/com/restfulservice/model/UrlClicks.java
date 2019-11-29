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
@Table(name="url_views")
public class UrlClicks {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	@Column(name="email")
	private String email;
	@Column(name="click_count")
	private Integer clickCount;
	@Column(name="templatename")
	private String templatename;
	@Column(name="url")
	private String url;
	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the clickCount
	 */
	public Integer getClickCount() {
		return clickCount;
	}
	/**
	 * @param clickCount the clickCount to set
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	/**
	 * @return the templatename
	 */
	public String getTemplatename() {
		return templatename;
	}
	/**
	 * @param templatename the templatename to set
	 */
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
