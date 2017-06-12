package com.leoni.forsimport.components.security;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.leoni.forsimport.services.Authenticator;

/**
 * Checks if the user is logged in, and only renders its body if the user
 * is logged in. Like the <em>if</em> component, an optional "else" parameter is
 * supported for a block to render when the user is <em>not</em> logged in.
 * 
 * @author karesti
 */
public class Authenticated extends AbstractConditional
{

    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test()
    {
        return authenticator.isLoggedIn();
    }

}
