package com.leoni.forsimport.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.leoni.forsimport.pages.Column;
import com.leoni.forsimport.pages.Table;

public class TableDAO {

	/**
	 * Returns an object representing the table structure.
	 * 
	 * @param table
	 * @return
	 */
	public Connection getConnection() {
		Connection connexion = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection("jdbc:postgresql://localhost:9090/test_vieExcel", "postgres",
					"radhoinbhk123456789");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connexion;
	}

	public Table getTableStructure(String tableName) {
		Table table = new Table();
		try {
			Connection connexion = getConnection();
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
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
