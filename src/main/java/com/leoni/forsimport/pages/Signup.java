package com.leoni.forsimport.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Secure;

import com.leoni.forsimport.annotations.AnonymousAccess;
import com.leoni.forsimport.services.Authenticator;

/**
 * This page the user can create an account
 * 
 * @author karesti
 */

@AnonymousAccess
@Secure
public class Signup {

	Authenticator authenticator;
	@InjectPage
	private Index index;

	public Object onActivate() {
		authenticator.logout();
		if (!authenticator.isLoggedIn()) {
			return index;
		}
		return null;
	}

}
