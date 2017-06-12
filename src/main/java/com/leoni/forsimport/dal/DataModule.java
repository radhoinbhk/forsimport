package com.leoni.forsimport.dal;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leoni.forsimport.model.User;
import com.leoni.forsimport.security.AuthenticationException;
import com.leoni.forsimport.services.Authenticator;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
public class DataModule
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    private final CrudServiceDAO dao;
    
    @Inject
    Authenticator authenticator;

    public DataModule(CrudServiceDAO dao)
    {
        super();
        this.dao = dao;
    }

    @Startup
    public void initialize() throws AuthenticationException
    {
        List<User> users = new ArrayList<User>();
        
        LOGGER.info("-- Loading initial demo data");
        create(users);
        List<User> userList = dao.findWithNamedQuery(User.ALL);
        LOGGER.info("Users " + userList);
        LOGGER.info("-- Data Loaded. Exit");
    }

    private void create(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.create(e);
        }
    }

}
