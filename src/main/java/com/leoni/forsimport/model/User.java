package com.leoni.forsimport.model;

import org.apache.tapestry5.validator.Email;

public class User {
private String emailUser;
private String typeUser;
private String mdpUser;
/**
 * @return the emailUser
 */
public String getEmailUser() {
	return emailUser;
}
/**
 * @param string the emailUser to set
 */
public void setEmailUser(String string) {
	this.emailUser = string;
}
/**
 * @return the typeUser
 */
public String getTypeUser() {
	return typeUser;
}
/**
 * @param typeUser the typeUser to set
 */
public void setTypeUser(String typeUser) {
	this.typeUser = typeUser;
}
/**
 * @return the mdpUser
 */
public String getMdpUser() {
	return mdpUser;
}
/**
 * @param mdpUser the mdpUser to set
 */
public void setMdpUser(String mdpUser) {
	this.mdpUser = mdpUser;
}

}
