package com.leoni.forsimport.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.leoni.forsimport.services.Authenticator;

/**
 * Layout component for all pages
 */
//@Import(stylesheet =
//{ "context:/static/style.css","context:/static/reset.css" }, library =
//{ "context:/static/hotel-booking.js" }) //
public class LayoutIndex
{
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Inject
    private Authenticator authenticator;

    @Inject
    private Messages messages;

    /**
     * Respond to the user clicking on the "Log Out" link
     */
    @Log
    public Object onActionFromLogout()
    {
        authenticator.logout();
        return Import.class;
    }

    /**
     * Return a "Welcome, John Smith" message
     * @return the message, or an empty string if there is no logged-in user
     */
    public String getWelcomeMessage()
    {
        if (authenticator.isLoggedIn())
        {
            return messages.format("nav.welcome", authenticator.getLoggedUser().getEmailUser());
        }
        return "";
    }
}
