package com.leoni.forsimport.test;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.User;

@Import(library = { "js/jquery.min.js", "js/bootstrap.min.js", "js/jquery.validation.min.js",
		"js/bootstrap-show-password.min.js", "js/TweenMax.min.js", "js/common.js", "js/demo.temp.js" })
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
		if (!isUser(password)) {
			login.recordError(emailField, "email and password incorect");
		}
	}

	private boolean isUser(String password) {
		// TODO Auto-generated method stub
		User user = new User();
		TableDAO dao = new TableDAO();
		user = dao.getUserByEmail(email);
		boolean r = false;
		if (user != null) {
				if (password.equals(user.getPassword())) {
					r = true;
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