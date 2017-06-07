package com.leoni.forsimport.pages;

import java.text.DateFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.User;
import com.leoni.forsimport.services.ExceptionUtil;

@Import(stylesheet = "context:mybootstrap/css/ajaxformsinaloop.css")
public class Administration {

	// Screen fields

	@Property
	private List<User> persons;

	@Property
	@Persist
	private Map<Integer, User> personsMap;

	@Property
	private User user;

	@Property
	private boolean editing;

	@Property
	@Persist
	private String newEmailUser;

	@Property
	@Persist
	private String newProfilUser;

	// @Property
	// private final String BAD_NAME = "Acme";

	// Work fields

	private boolean loadingLoop;

	// Generally useful bits and pieces

	// @EJB
	// private TableDAO personFinderService;
	//
	// @EJB
	// private TableDAO personManagerService;

	@InjectComponent
	private Zone rowZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Form personForm;

	@InjectComponent
	private Form newPersonForm;

	@Inject
	private Messages messages;

	@Inject
	private Locale currentLocale;

	// The code

	void onActivate() {
		loadingLoop = false;
	}

	void setupRender() {
		loadingLoop = true;

		// Get all persons - ask business service to find them (from the
		// database)
		TableDAO dao = new TableDAO();
		persons = dao.getUsers();
		personsMap = new HashMap<>();
		if (persons != null) {
			for (User user : persons) {
				personsMap.put(user.getId(), user);
			}
		}
	}

	void onPrepareForRenderFromPersonForm(int personId) {

		// If the loop is being reloaded, the form may have had errors so clear
		// them just in case.

		if (loadingLoop) {
			personForm.clearErrors();
			editing = false;
		}

		// If the form is valid then we're not redisplaying due to error, so get
		// the person.

		if (personForm.isValid()) {
			// person = personFinderService.findPerson(personId);
			user = personsMap.get(personId);
			// Handle null person in the template.
		}
	}

	void onPrepareForSubmitFromPersonForm(int personId) {

		// Get objects for the form fields to overlay.
		user = personsMap.get(personId);

	}

	void onValidateFromPersonForm() {

		if (personForm.getHasErrors()) {
			return;
		}

		try {
			TableDAO dao = new TableDAO();
			dao.changeUser(user, user.getId());
		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			personForm.recordError(ExceptionUtil.getRootCause(e));
		}

	}

	void onSuccessFromPersonForm() {

		editing = false;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(rowZone);
		}
	}

	void onFailureFromPersonForm() {

		editing = true;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(rowZone);
		}
	}

	void onToDelet(int personId) {
		TableDAO dao = new TableDAO();
		dao.deletUser(personId);
	}

	void onToEdit(int personId) {
		// person = personFinderService.findPerson(personId);
		user = personsMap.get(personId);
		editing = true;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(rowZone);
		}
	}

	void onCancel(int personId) {
		// person = personFinderService.findPerson(personId);
		user = personsMap.get(personId);
		editing = false;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(rowZone);
		}
	}

	void onValidateFromNewPersonForm() {
		TableDAO dao = new TableDAO();
		user.setEmailUser(newEmailUser);
		user.setProfilUser(newProfilUser);
		dao.addUser(user);
	}

	public String getCurrentRowZoneId() {
		// The id attribute of a row must be the same every time that row asks
		// for it and unique on the page.
		return "rowZone_" + user.getId();
	}

	public Format getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
	}

	public boolean isPersonFormHasErrors() {
		return personForm.getHasErrors();
	}

}