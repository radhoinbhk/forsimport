package com.leoni.forsimport.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.leoni.forsimport.model.User;
import com.leoni.forsimport.services.Authenticator;

/**
 * @author HP
 *
 */
public class BasePage {

	@Inject
	private Authenticator authenticator;
	
	@InjectPage
	private Index index;

	public Object onActivate() {
		if (!authenticator.isLoggedIn()) {
			return index;
		}
		return null;
	}
	
    public boolean isDeveloper() {
		String profile = null ;
		User user = authenticator.getLoggedUser();
		if (user != null) {
			profile = user.getProfilUser();
		}
		return "developer".equals(profile);
	}


}
