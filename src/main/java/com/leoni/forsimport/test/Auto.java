package com.leoni.forsimport.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.leoni.forsimport.services.TabNames;

public class Auto {
	
	// Screen fields
    @Property
    @Persist
    private String tableName;

    // Generally useful bits and pieces
    @Inject
    private TabNames tableNames;

    // The code
    List<String> onProvideCompletionsFromtableName(String partial) {
    	List<String> matches = new ArrayList<String>();
        partial = partial.toUpperCase();

        for (String countryName : tableNames.getTableNames()) {
            if (countryName.toUpperCase().contains(partial)) {
                matches.add(countryName);
            }
        }

        return matches;
    }
}
