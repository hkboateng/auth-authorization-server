package com.protecksoftware.authentication.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This is a super entity that include Create date, update date and users.
 * @author hkyeremateng-boateng
 *
 */
@MappedSuperclass
public class ProteckEntity {

	@Column(name="createDate")
	private Date createDate;
	@Column(name="updateDate")
	private Date updateDate;
	@Column(name="updateUser")
	private String updateUser;
	@Column(name="createUser")
	private String createUser;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	
}
