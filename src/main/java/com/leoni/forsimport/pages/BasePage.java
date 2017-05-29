package com.leoni.forsimport.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

/**
 * @author HP
 *
 */
public class BasePage {
	

    @Property
    private String email;


    @Inject
    private Request request;

    // The code

    public boolean getHasSession() {
        return request.getSession(false) != null;
    }

    public Session getSession() {
        return request.getSession(false);
    }

    public Object getAttributeValue() {
        return getSession().getAttribute(email);
    }
}
