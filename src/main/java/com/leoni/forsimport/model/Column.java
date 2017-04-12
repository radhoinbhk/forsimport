package com.leoni.forsimport.model;

public class Column {

	private String columnName;
	String columnType;
	private int nullable;
	private boolean primaryKey;
	private boolean defaultValue;
	private boolean autoIncrement;
	private Integer precision;
	private Integer positioncolumn;
	private Integer scale;
	private String columnTypeJdbc;

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the columnTypeJdbc
	 */

	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		switch (columnType) {
		case "bpchar":
			
			break;

		default:
			break;
		}
		if (columnType.equals("bpchar")) {
			return "char (" + getPrecision() + ")";
		} else if (columnType.equals("numeric")) {
			return columnType + " (" + getPrecision() + "," + getScale() + ")";
		} else {
			return columnType + " (" + getPrecision() + ")";
		}
	}

	/**
	 * @param columnType
	 *            the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the nullable
	 */
	public String isNullable() {
		if (nullable == 1) {
			return "YES";
		} else {
			return " NO ";
		}
	}

	/**
	 * @param i
	 *            the nullable to set
	 */
	public void setNullable(int i) {
		this.nullable = i;
	}

	/**
	 * @return the primaryKey
	 */
	public String isPrimaryKey() {
		if (primaryKey) {
			return "YES";
		} else {
			return " NO ";
		}
	}

	/**
	 * @param primaryKey
	 *            the primaryKey to set
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the defaultValue
	 */
	public boolean getDefaultValue() {
		return defaultValue;
		
	}

	

	/**
	 * @param b
	 *            the defaultValue to set
	 */
	public void setDefaultValue(boolean b) {
		this.defaultValue = b;
	}

	/**
	 * @return the autoIncrement
	 */
	public String isAutoIncrement() {
		if (autoIncrement) {
			return "YES";
		} else {
			return " NO ";
		}
	}

	/**
	 * @param autoIncrement
	 *            the autoIncrement to set
	 */
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	/**
	 * @return the getPrecision
	 */
	public Integer getPrecision() {
		return precision;
	}

	/**
	 * @param getPrecision
	 *            the getPrecision to set
	 */
	public void setPrecision(Integer getPrecision) {
		this.precision = getPrecision;
	}

	/**
	 * @return the positioncolumn
	 */
	public Integer getPositioncolumn() {
		return positioncolumn;
	}

	/**
	 * @param positioncolumn
	 *            the position column to set.
	 */
	public void setPositioncolumn(Integer positioncolumn) {
		this.positioncolumn = positioncolumn;
	}

	/**
	 * @return the scale.
	 */
	public Integer getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set.
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}

	/**
	 * @return the columnTypeJdbc.
	 */
	public String getColumnTypeJdbc() {
		return columnTypeJdbc.toUpperCase();
	}

	/**
	 * @param columnTypeJdbc
	 *            the columnTypeJdbc to set.
	 */
	public void setColumnTypeJdbc(String columnTypeJdbc) {
		this.columnTypeJdbc = columnTypeJdbc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return columnName + ":" + columnType;
	}

}