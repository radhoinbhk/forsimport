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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.leoni.forsimport.model.Column;
import com.leoni.forsimport.model.Table;
import com.leoni.forsimport.model.User;
import com.sun.xml.internal.ws.api.message.AddressingUtils;

/**
 * class DAO
 * 
 */
public class TableDAO {

	private static final Logger LOG = Logger.getLogger(TableDAO.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * Returns an object representing the table structure.
	 * 
	 * @param table
	 * @return
	 */

	/**
	 * getConnection method return the connection of data base
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

	/**
	 * getTableStructure method Returns the structure of the table "tableName"
	 */
	public Table getTableStructure(String tableName) {
		Table table = new Table();
		try {
			Connection connexion = getConnection();
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName );
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
	
	/**
	 * {@inheritDoc}
	 */
	public User getUserByEmail(String email) {
		try {
			Query query = entityManager.createQuery("select u from users u where u.email like :email");
			query.setParameter("email", email);
			User user = (User) query.getSingleResult();
			return user;
		} catch (RuntimeException re) {
			return null ;
		}
	}
	
	/**
	 * It is a method return all the data from the table users
	 * @param email 
	 * 
	 * @return
	 */
//	public User getUserByEmail(String email) {
//		try {
//			Connection connexion = getConnection();
//			PreparedStatement st = connexion.prepareStatement("select * from users where email= ?");
//			st.setString(1,email );
//			ResultSet rs = st.executeQuery();
//			User user = new User();
//			while (rs.next()) {
//				user.setId(rs.getInt("id"));
//				user.setEmailUser(rs.getString("email"));
//				user.setMdpUser(rs.getString("password"));
//				user.setProfilUser(rs.getString("profiluser"));
//			}
//			connexion.close();
//			return user;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//
//	}
	
	/**
	 * It is a method return all the data from the table users
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();

		try {
			Connection connexion = getConnection();
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from users");
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmailUser(rs.getString("emailuser"));
				user.setPassword(rs.getString("password"));
				user.setProfilUser(rs.getString("profiluser"));
				users.add(user);
			}
			connexion.close();
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * It is a method that allows to change "user" data
	 * 
	 * @param user
	 * @param id
	 */
	public void changeUser(User user, int id) {
		try {
			Connection connexion = getConnection();
			String sql = "UPDATE users SET emailuser = ?, password = ?, profiluser = ? WHERE id = ?";
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setString(1, user.getEmailUser());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getProfilUser());
			pstmt.setInt(4, user.getId());
			pstmt.executeUpdate();
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * getProfilUser It is a method that allows to select the data user where
	 * emailUser equal user
	 * 
	 * @param Email
	 * @return
	 */
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

	/**
	 * addUser It is a method that adds user
	 * 
	 * @param user
	 */
	public  void addUser(User user) {
		// TODO Auto-generated method stub
		try {
			Connection connexion = getConnection();
			PreparedStatement prepare = connexion
					.prepareStatement("INSERT INTO users(emailuser,password,profiluser) VALUES(?,?,?)");
			prepare.setString(1, user.getEmailUser());
			prepare.setString(3, user.getProfilUser());
			String password = createRandomCode(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			prepare.setString(2, "123456");
			prepare.executeUpdate();
			connexion.close();
		} catch (Exception e) {
			// Message
			System.out.println(e);
		}
	}

	/**
	 * createRandomCode It is a method that allows to create new password
	 * 
	 * @param codeLength
	 * @param id
	 * @return
	 */
	public String createRandomCode(int codeLength, String id) {
		List<Character> temp = id.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
		Collections.shuffle(temp, new SecureRandom());
		return temp.stream().map(Object::toString).limit(codeLength).collect(Collectors.joining());
	}

	/***
	 * deletUser It is a method that allows to Delete user where id = "personId"
	 * 
	 * @param personId
	 */
	public void deletUser(int personId) {
		// TODO Auto-generated method stub
		try {
			Connection connexion = getConnection();
			PreparedStatement prepare = connexion.prepareStatement("delete from users where id=?");
			prepare.setInt(1, personId);
			prepare.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void insert(Sheet sheet, String tableName) {
		// TODO Auto-generated method stub
		Connection connexion = getConnection();
		Statement stmt;
		Date d = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyyddMM'_'hhmmss");
		String date =formater.format(d);
		try {
			stmt = connexion.createStatement();
			StringBuilder requet1 = new StringBuilder("create table ")
					.append(tableName)
					.append("_")
					.append(date)
					.append(" as select * from ")
					.append(tableName);
			stmt.execute(requet1.toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rows - 1; i++) {
			String requet = getRequetValue(sheet.getRow(i), sheet.getRow(0), tableName);
			try {
				PreparedStatement prepare = connexion.prepareStatement(requet);
				prepare.executeUpdate();
			} catch (Exception e) {
				// Message
			}
		}

	}

	private String getRequetValue(Row r, Row r0, String tableName) {
		// TODO Auto-generated method stub

		String requetParti1 = "";
		String requetParti2 = "";
		for (int j = 0; j < r.getLastCellNum() - 1; j++) {
			Cell cell = r.getCell(j);
			requetParti1 = requetParti1 + r0.getCell(j).getStringCellValue() + ",";
			if (cell.getCellTypeEnum() == CellType.STRING) {
				requetParti2 = requetParti2 + "'" + cell.getStringCellValue() + "',";
			} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				requetParti2 = requetParti2 + cell.getNumericCellValue() + ",";
			}
		}
		requetParti1 = requetParti1 + r0.getCell(r.getLastCellNum() - 1).getStringCellValue();
		if (r.getCell(r.getLastCellNum() - 1).getCellTypeEnum() == CellType.STRING) {
			requetParti2 = requetParti2 + "'" + r.getCell(r.getLastCellNum() - 1).getStringCellValue() + "'";
		} else if (r.getCell(r.getLastCellNum() - 1).getCellTypeEnum() == CellType.NUMERIC) {
			requetParti2 = requetParti2 + r.getCell(r.getLastCellNum() - 1).getNumericCellValue();
		}
		return "INSERT INTO " + tableName + "(" + requetParti1 + ") VALUES(" + requetParti2 + ")";
	}
}
