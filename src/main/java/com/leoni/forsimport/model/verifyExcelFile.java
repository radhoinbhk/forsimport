package com.leoni.forsimport.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.pages.Import;
import com.sun.jna.platform.win32.WinUser.SIZE;

public class verifyExcelFile {

	Error error = new Error();
	private ArrayList<Error> errors = new ArrayList();
	private static final Logger LOG = Logger.getLogger(Import.class);

	public ArrayList<Error> testColumnOrder(ArrayList<Cell> cells, String tableName) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			int type = cell.getCellType();
			if ((type != CellType.STRING.getCode()) && (cell.getStringCellValue() != columns.get(i).getColumnName())
					&& (cells.size() != columns.size())) {

				error.setNumCellError(i);
				error.setTypeError("The cell is false or Cell location is false");
				errors.add(error);
			}
		}
		return errors;
	}

	public ArrayList<Error> testType(ArrayList<Cell> cells, String tableName) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		for (int i = 0; i < cells.size(); i++) {
			Column column = columns.get(i);
			Cell cell = cells.get(i);
			int type = cell.getCellType();
			String typeTable = column.getColumnType();
			if (type != typeTable.hashCode()) {

				error.setNumCellError(i);
				error.setTypeError("The type for cell is false");
				errors.add(error);
			}
		}
		return errors;
	}

	public ArrayList<Error> testSize(ArrayList<Cell> cells, String tableName) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		for (int i = 0; i < cells.size(); i++) {
			Column column = columns.get(i);
			Cell cell = cells.get(i);
			int type = cell.getCellType();
			int size = 0;
			if(type == CellType.STRING.getCode())
			{
				size= cell.getStringCellValue().length();
			}else if(type == CellType.NUMERIC.getCode()){
				size= String.valueOf(cell.getStringCellValue()).length();
			}else{
				
			}
			if (type != column.getColumnType().hashCode()) {

				error.setNumCellError(i);
				error.setTypeError("The cell is false or Cell location is false");
				errors.add(error);
			}
		}
		return errors;
	}

	public static void main(String[] args) {
		double d= 234.12413;
		String text = Double.toString(Math.abs(d));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
	}
}
// else if (type == CellType.NUMERIC.getCode()) {
// LOG.info("numeric" + cell.getNumericCellValue());
// } else if (type == CellType.BOOLEAN.getCode()) {
// LOG.info("boolean" + cell.getBooleanCellValue());
// }