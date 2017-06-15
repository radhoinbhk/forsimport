package com.leoni.forsimport.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leoni.forsimport.annotations.AnonymousAccess;
import com.leoni.forsimport.security.AuthenticationException;
import com.leoni.forsimport.services.Authenticator;

/**
 * User can sign up on the
 * 
 * @author karesti
 */
@AnonymousAccess
@Secure
@Import(library = {"js/jquery.min.js", "js/bootstrap.min.js", "js/jquery.validation.min.js",
"js/bootstrap-show-password.min.js", "js/TweenMax.min.js", "js/common.js", "js/demo.temp.js" })
public class Index {
	private final static Logger LOG = LoggerFactory.getLogger(Index.class);

	@Property
	private String emailUser;

	@Property
	private String password;

	@Property
	String target; // target page name

	@Inject
	private Authenticator authenticator;

	@Component
	private Form loginForm;

	@Inject
	private Messages messages;

	@Inject
	private AlertManager alertManager;

	/**
	 * Respond to page activation by capturing the "target" path info as the
	 * name of the target page (the page to return to after login)
	 * 
	 * @param context
	 *            the EventContext
	 */
	public void onActivate(EventContext context) {
		if (context.getCount() > 0) {
			target = context.get(String.class, 0);
		} else {
			target = "Import";
		}
	}

	@Log
	public Object onSubmitFromLoginForm() {
		LOG.debug("onSubmitFromLoginForm");
		try {
			authenticator.login(emailUser, password);
		} catch (AuthenticationException ex) {
			// bad username or password entered
			loginForm.recordError("email or password incorrect");
			return null;
		}
		// was login successful?
		if (authenticator.isLoggedIn()) {
			// display a transient "success" message
			alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS,
					messages.format("welcome", authenticator.getLoggedUser().getEmailUser()));

			// redirect to the page the user wanted before being sent to the
			// login page
			return target;
		}
		return Index.class;
	}
}
