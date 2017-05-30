package com.leoni.forsimport.dao;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.leoni.forsimport.model.Column;
import com.leoni.forsimport.model.Table;
import com.leoni.forsimport.model.User;

public class TableDAO {

	private static final Logger LOG = Logger.getLogger(TableDAO.class);

	/**
	 * Returns an object representing the table structure.
	 * 
	 * @param table
	 * @return
	 */
	public Connection getConnection() {
		Connection connexion = null;
		try {
			Properties properties = new Properties();
			properties.load(TableDAO.class.getResourceAsStream("db.properties"));
			Class.forName(properties.getProperty("db_driver"));
			connexion = DriverManager.getConnection(properties.getProperty("db_url"), properties.getProperty("db_user"),
					properties.getProperty("db_password"));
		} catch (IOException e1) {
			LOG.error("File db.properties cannot be read", e1);
		} catch (ClassNotFoundException e) {
			LOG.error("Postgres driver could not be loaded", e);
		} catch (SQLException e) {
			LOG.error("Connection could not be established", e);
		}

		return connexion;
	}

	public Table getTableStructure(String tableName) {
		Table table = new Table();
		try {
			Connection connexion = getConnection();
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName /* + " limit 1" */);
			ResultSetMetaData rsmd = rs.getMetaData();
			ArrayList<Column> columns = new ArrayList<Column>();

			/* get the column name of the primary key */
			ResultSet pkColumns = connexion.getMetaData().getPrimaryKeys(null, null, tableName);
			String pkColumnName = null;
			while (pkColumns.next()) {
				pkColumnName = pkColumns.getString("COLUMN_NAME");
			}

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {

				Column column = new Column();
				column.setColumnName(rsmd.getColumnName(i));
				column.setColumnType(rsmd.getColumnTypeName(i));
				column.setDefaultValue(rsmd.isCaseSensitive(i));
				if (rsmd.getColumnName(i) == pkColumnName) {
					column.setPrimaryKey(true);
				}
				column.setAutoIncrement(rsmd.isAutoIncrement(i));
				column.setPrecision(rsmd.getPrecision(i));
				column.setPositioncolumn(i);
				column.setScale(rsmd.getScale(i));
				column.setNullable(rsmd.isNullable(i));
				column.setColumnTypeJdbc(rsmd.getColumnTypeName(i));
				columns.add(column);

			}

			table.setColumns(columns);
			table.setTableName(tableName);
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return table;
	}

	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();

		try {
			Connection connexion = getConnection();
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from users");
			int i = -1;
			while (rs.next()) {
				User user = new User();
				i++;
				user.setIdUser(rs.getInt("id"));
				user.setEmailUser(rs.getString("emailuser"));
				user.setMdpUser(rs.getString("mdpuser"));
				user.setTypeUser(rs.getString("typeuser"));
				users.add(i, user);
			}
			connexion.close();
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void changeUser(User user, int id) {
		try {
			Connection connexion = getConnection();
			String sql = "UPDATE users SET emailuser = ?, typeuser = ?, mdpuser = ? WHERE id = ?";
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setString(1, user.getEmailUser());
			pstmt.setString(2, user.getTypeUser());
			pstmt.setString(3, user.getMdpUser());
			pstmt.setInt(4, user.getIdUser());
			pstmt.executeUpdate();
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProfilUser(String Email) {
		try {
			Connection connexion = getConnection();
			PreparedStatement statement = connexion.prepareStatement("select typeuser from users WHERE emailuser = ?");
			statement.setString(1, Email);
			ResultSet resultSet = statement.executeQuery();
			connexion.close();
			return resultSet.getString(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		try{
			Connection connexion = getConnection();
		     PreparedStatement prepare = connexion.prepareStatement("INSERT INTO parrain VALUES(null,?,?,?)");
		     prepare.setString (1, user.getEmailUser());
		     prepare.setString(2, user.getTypeUser());
		     String password = createRandomCode(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		     prepare.setString (3,password);
		 
		     prepare.executeUpdate();
		}catch(Exception e){
		     // Message
		}
	}
	
	/*
	 * the methode creat a new password
	 * */
	public String createRandomCode(int codeLength, String id) {   
	    List<Character> temp = id.chars()
	            .mapToObj(i -> (char)i)
	            .collect(Collectors.toList());
	    Collections.shuffle(temp, new SecureRandom());
	    return temp.stream()
	            .map(Object::toString)
	            .limit(codeLength)
	            .collect(Collectors.joining());
	}
}
