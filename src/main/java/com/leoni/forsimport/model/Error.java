package com.leoni.forsimport.model;

import org.apache.poi.ss.usermodel.Cell;

public class Error {
	
	private String typeError ;
	private int numCellError ;
	/**
	 * @return the typeError
	 */
	public String getTypeError() {
		return typeError;
	}
	/**
	 * @param typeError the typeError to set
	 */
	public void setTypeError(String typeError) {
		this.typeError = typeError;
	}
	/**
	 * @return the locationError
	 */
	public int getNumCellError() {
		return numCellError;
	}
	/**
	 * @param locationError the locationError to set
	 */
	public void setNumCellError(int numCellError) {
		this.numCellError = numCellError;
	}

	
	
}
