package com.example.addressbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Contacts")
public class Contact {
@Id
private String mobile;
private String cname;
@Column(name="mailid")
private String mailId;

/**
 * @return the mobile
 */
public String getMobile() {
	return mobile;
}
/**
 * @param mobile the mobile to set
 */
public void setMobile(String mobile) {
	this.mobile = mobile;
}
/**
 * @return the cname
 */
public String getCname() {
	return cname;
}
/**
 * @param cname the cname to set
 */
public void setCname(String cname) {
	this.cname = cname;
}
/**
 * @return the mailId
 */
public String getMailId() {
	return mailId;
}
/**
 * @param mailId the mailId to set
 */
public void setMailId(String mailId) {
	this.mailId = mailId;
}

}
