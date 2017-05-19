package com.leoni.forsimport.pages;

import java.util.ArrayList;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.openqa.selenium.remote.server.handler.GetCurrentUrl;
import org.slf4j.Logger;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.User;

public class Login {
	@Inject
	private Logger logger;

	@Inject
	private AlertManager alertManager;

	@InjectComponent
	private Form login;

	@InjectComponent("email")
	private TextField emailField;

	@InjectComponent("password")
	private PasswordField passwordField;

	@Property
	private String email;

	@Property
	private String password;

	void onValidateFromLogin() {
		User user = new User();
		ArrayList<User> users = new ArrayList<>();
		TableDAO dao = new TableDAO();
		for (int i = 0; i < users.size(); i++) {
			users = dao.getUser();
		if (!email.equals(user.getEmailUser())) {
			login.recordError(emailField, "Try with user: users@tapestry.apache.org");
		}
		if (!password.equals(user.getMdpUser())) {
			login.recordError(passwordField, "Try with password: Tapestry5");
		}
		}
	}


//	public boolean isPasswordUser(String password2) {
//		User user = new User();
//		ArrayList<User> users = new ArrayList<>();
//		TableDAO dao = new TableDAO();
//		users = dao.getUser();
//		boolean r = true;
//		for (int i = 0; i < users.size(); i++) {
//			user = users.get(i);
//			if (!password.equals(user.getMdpUser())) {
//				r = false;
//			}
//		}
//		return r;
//	}

	Object onSuccessFromLogin() {
		logger.info("Login successful!");
		alertManager.success("Welcome aboard!");

		return Index.class;
	}

	void onFailureFromLogin() {
		logger.warn("Login error!");
		alertManager.error("I'm sorry but I can't log you in!");
	}

}
