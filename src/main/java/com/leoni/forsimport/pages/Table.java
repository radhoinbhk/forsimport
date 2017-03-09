package com.leoni.forsimport.pages;

import java.util.ArrayList;

public class Table {

	private String tableName ;
	private ArrayList<Column> columns;
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the columns
	 */
	public ArrayList<Column> getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}
	

}
