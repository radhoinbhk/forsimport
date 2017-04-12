package com.leoni.forsimport.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.pages.Import;
import com.leoni.forsimport.services.ExcelStreamResponse;

public class VerifyExcelFile {

	private static final Logger LOG = Logger.getLogger(Import.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	private CellStyle errorCellStyle;
	private CellStyle okCellStyle;
	private Drawing drawing;
	private ClientAnchor anchor;
	private CreationHelper factory;
	private Workbook workbook = null;
	/*
	 * getExcelFileverify it is a method return the EXCEL file verify
	 */

	public ExcelStreamResponse getExcelFileverify(File file, String fileName2) {
		String fileName = "test";
		// TODO name for table
		// int index = fileName.indexOf(".");
		// String tableName = fileName.substring(0, index);
		// LOG.info("444444444444***********" + tableName);
		// TODO Auto-generated method stub
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheetAt(0);
		errorCellStyle = workbook.createCellStyle();
		errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		errorCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		okCellStyle = workbook.createCellStyle();
		okCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		okCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		factory = workbook.getCreationHelper();
		drawing = sheet.createDrawingPatriarch();
		anchor = factory.createClientAnchor();
		for (Row r : sheet) {
			ArrayList<Cell> cells = new ArrayList<Cell>();
			for (Cell cell : r) {
				cells.add(cell);
			}
			if (r.getRowNum() == 0) {
				testColumnOrder(cells, fileName, r);
			} else {
				globalTest(cells, fileName, r);
			}
		}

		FileOutputStream out = null;

		File fileOut = new File(fileName);
		try {
			out = new FileOutputStream(fileOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tempFolder = System.getProperty("java.io.tmpdir");
		LOG.info("Temp folder :" + tempFolder);
		try {
			File filetemp = new File(
					tempFolder + File.separator + fileName + "_" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream excelFileOut = new FileOutputStream(filetemp);
			workbook.write(excelFileOut);
			excelFileOut.close();
			// Thread.sleep(10000);
			return new ExcelStreamResponse(new FileInputStream(filetemp), fileName);
		} catch (FileNotFoundException e) {
			LOG.error("File not found", e);
			return null;
		} catch (IOException e) {
			LOG.error("Unknown error", e);
			return null;
		} /*
			 * catch (InterruptedException e) { LOG.error("Unknown error", e);
			 * return null; }
			 */
	}

	/*
	 * testColumnOrder It is a method to test the order of the column
	 */
	public void testColumnOrder(ArrayList<Cell> cells, String tableName, Row r) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			if (cells.size() != columns.size()) {
				// TODO message
				markErroneousCell(cells.get(i), "size of cell and size of column not equals", r);
				size = cells.size();
			} else {
				Cell cell = cells.get(i);
				int type = cell.getCellType();
				if (type != CellType.STRING.getCode()) {
					// TODO message
					markErroneousCell(cell, "The cell is not String", r);
				} else if (!cell.getStringCellValue().equals(columns.get(i).getColumnName())) {
					// TODO message
					markErroneousCell(cell, "false name cell", r);
				}
			}
		}
	}

	/**
	 * marks the given cell as erroneous and creates a comment with an error
	 * message.
	 * 
	 * @param cell
	 * @param message
	 * @param r
	 */
	private void markErroneousCell(Cell cell, String message, Row r) {
		cell.setCellStyle(errorCellStyle);
		// TODO the problem for comment where cell >>>
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex() + 2);
		anchor.setRow1(r.getRowNum());
		anchor.setRow2(r.getRowNum() + 3);
		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString(message);
		comment.setString(str);
		comment.setAuthor("Apache POI");
		cell.setCellComment(comment);
	}

	/*
	 * globalTest This is a method to test all the errors found in the column
	 */

	public void globalTest(ArrayList<Cell> cells, String tableName, Row r) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		for (int i = 0; i < cells.size(); i++) {
			Column column = columns.get(i);
			Cell cell = cells.get(i);
			testType(column, cell, i, r);
			testSize(column, cell, i, r);
		}
	}

	private void testSize(Column column, Cell cell, int numCell, Row r) {
		// TODO Auto-generated method stub
		CellType type = cell.getCellTypeEnum();
		int integerSize = 0;
		int decimalSize = 0;
		LOG.info(column);
		if (type == CellType.STRING) {
			integerSize = cell.getStringCellValue().length();
			if (integerSize > column.getPrecision()) {
				LOG.info("integerSize"+integerSize);
				LOG.info("column.getPrecision()"+column.getPrecision());
				// TODO message
				markErroneousCell(cell, "The size of the cell is a falseiiiiiii", r);
			}
		} else if (type == CellType.NUMERIC) {
			double d = cell.getNumericCellValue();
			String text = Double.toString(Math.abs(d));
			 integerSize = text.indexOf('.');
			 String.valueOf(cell.getNumericCellValue()).length();
			decimalSize = text.length()- integerSize - 1;
			if ((decimalSize == 1) && (text.charAt(integerSize+1)=='0')&&(integerSize > column.getPrecision())) {
					// TODO message
					markErroneousCell(cell, "The size of the cell is a false", r);
			} else if ((integerSize > column.getPrecision()) || (decimalSize > column.getScale())) {
					// TODO message
					markErroneousCell(cell, "The size of the cell is a falseqqqqq", r);
				}			
		}

		
	}

	private void testType(Column column, Cell cell, int numCell, Row r) {
		// TODO Auto-generated method stub
		CellType type = cell.getCellTypeEnum();
		String typeTable = column.columnType;
		LOG.info(typeTable);
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue()/* 2016/02/03" */;
				CellStyle cellStyle = workbook.createCellStyle();
				CreationHelper createHelper = workbook.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyyMMdd"));
				cell.setCellValue(date);
				cell.setCellStyle(cellStyle);
			}
		}
		if ((typeTable.equals("varchar")) && (type != CellType.STRING)) {
			// cellTypeTable = CellType.STRING;
			markErroneousCell(cell, "The type for cell is false", r);
		} else if ((typeTable.equals("int4")) && (type != CellType.NUMERIC)) {
			// cellTypeTable = CellType.NUMERIC;
			markErroneousCell(cell, "The type for cell is false", r);
		}else if((typeTable.equals("boolean"))&&(type != CellType.BOOLEAN)){
			markErroneousCell(cell, "The type for cell is false", r);
		}
	}

	

}
