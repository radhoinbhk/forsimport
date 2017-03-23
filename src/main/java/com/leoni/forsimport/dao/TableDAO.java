package com.leoni.forsimport.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.leoni.forsimport.model.Column;
import com.leoni.forsimport.model.Table;

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
			ResultSet rs = stmt.executeQuery("select * from " + tableName + " limit 1");
			ResultSetMetaData rsmd = rs.getMetaData();
			ArrayList<Column> columns = new ArrayList<Column>();

			/* get the column name of the primary key */
			ResultSet pkColumns = connexion.getMetaData().getPrimaryKeys(null, null, "test");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return table;
	}

}
