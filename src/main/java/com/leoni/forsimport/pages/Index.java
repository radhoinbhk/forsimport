package com.leoni.forsimport.pages;

import java.util.ArrayList;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.User;
@Import(library={"js/jquery.min.js", "js/bootstrap.min.js",
		"js/jquery.validation.min.js", "js/bootstrap-show-password.min.js",
		"js/TweenMax.min.js", "js/common.js", "js/demo.temp.js" })
public class Index {
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
		if (!isEmailUser(email)) {
			login.recordError(emailField, "Try with user: users@tapestry.apache.org");
		}
		if (!isPasswordUser(password)) {
			login.recordError(passwordField, "Try with password: Tapestry5");
		}
	}

	private boolean isEmailUser(String email2) {
		// TODO Auto-generated method stub
		User user = new User();
		ArrayList<User> users = new ArrayList<>();
		TableDAO dao = new TableDAO();
		users = dao.getUser();
		boolean r = false;
		for (int i = 0; i < users.size(); i++) {
			user = users.get(i);
			if (email2.equals(user.getEmailUser())) {
				r = true;
				break;
			}
		}
		return r;
	}

	public boolean isPasswordUser(String password2) {
		User user = new User();
		ArrayList<User> users = new ArrayList<>();
		TableDAO dao = new TableDAO();
		users = dao.getUser();
		boolean r = false;
		for (int i = 0; i < users.size(); i++) {
			user = users.get(i);
			if (password2.equals(user.getMdpUser())) {
				r = true;
				break;
			}
		}
		return r;
	}

	Object onSuccessFromLogin() {
		logger.info("Login successful!");
		alertManager.success("Welcome aboard!");
		return com.leoni.forsimport.pages.Import.class;
	}

	void onFailureFromLogin() {
		logger.warn("Login error!");
		alertManager.error("I'm sorry but I can't log you in!");
	}
	
}