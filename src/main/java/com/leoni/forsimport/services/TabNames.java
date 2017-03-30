package com.leoni.forsimport.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.leoni.forsimport.dao.TableDAO;
/**
 * Provides a sorted set of table names 
 */
public class TabNames {
	
	private List<String> result = new ArrayList<>();
	
	public TabNames() {
		System.out.println("TabNames.TabNames()");
		prepareTableNames();
	}

	public static void main(String[] args) {
//		TabNames names = new TabNames();
//		names.getTableNames();
	}
	
	/**
	 * returns the list of available tables in the database.
	 * @return
	 */
	public void prepareTableNames() {
		TableDAO dao = new TableDAO();
		Connection conn = dao.getConnection();
		DatabaseMetaData md = null;
		ResultSet rs = null;
		try {
			md = conn.getMetaData();
			String[] types = {"TABLE"};
			rs = md.getTables(null, null, "%", types);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				System.out.println(rs.getString(3));
				result.add(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
			if (rs != null) {
					rs.close();
			}
			if (conn != null) {
				conn.close();
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	 public List<String> getTableNames() {
	        return Collections.unmodifiableList(result);
	    }

}