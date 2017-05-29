package com.leoni.forsimport.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.pages.Import;
import com.leoni.forsimport.services.ExcelStreamResponse;
import com.monitorjbl.xlsx.StreamingReader;

public class VerifyExcelFile {

	private static final Logger LOG = Logger.getLogger(Import.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	private CellStyle errorCellStyle;
	private CellStyle okCellStyle;
	private Drawing drawing;
	private ClientAnchor anchor;
	private CreationHelper factory;
	private Workbook workbook;
	File fileOut;
	/*
	 * getExcelFileverify it is a method return the EXCEL file verify
	 */

	public ExcelStreamResponse getExcelFileverify(File file, String fileName) {
		// TODO name for table
		// int index = fileName.indexOf(".");
		// String tableName = fileName.substring(0, index);
		// LOG.info("444444444444***********" + tableName);
		// TODO Auto-generated method stub
		// FileInputStream inputStream = null;
		fileOut = new File(fileName);
		try {
			// inputStream = new FileInputStream(file);
			workbook = WorkbookFactory.create(file);
			// workbook =
			// StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(inputStream);
			// workbook = new XSSFWorkbook(inputStream);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = 0; i < rows - 1; i++) {
			Row r = sheet.getRow(i);
			if (r != null) {
				if (r.getRowNum() == 0) {
					testColumnOrder(fileName, r);
				}
				if (r.getRowNum() != 0 && r.getLastCellNum() > 0) {
					globalTest(fileName, r);
				}
			}
		}
		if (isOkExcelStyle(sheet)) {
			 insert(sheet, fileName);
		}
		try {
			FileOutputStream out = new FileOutputStream(fileOut);
			workbook.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tempFolder = System.getProperty("java.io.tmpdir");
		try {
			File filetemp = new File(
					tempFolder + File.separator + fileName + "_" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream excelFileOut = new FileOutputStream(filetemp);
			workbook.write(excelFileOut);
			excelFileOut.close();
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

	private void insert(Sheet sheet, String fileName) {
		// TODO Auto-generated method stub
		
	}

	private boolean isOkExcelStyle(Sheet sheet) {
		// TODO Auto-generated method stub

		int rows = sheet.getPhysicalNumberOfRows();
		int sizeRow = 0;
		for (int i = 1; i < rows - 1; i++) {
			Row r = sheet.getRow(i);
			int sizeCell = 0;
			for (int j = 0; j < r.getLastCellNum(); j++) {
				Cell cell = r.getCell(i);
				if (cell.getCellStyle().getFillForegroundColor() == IndexedColors.GREEN.getIndex()) {
					sizeCell++;
				}
			}
			if (sizeRow == r.getLastCellNum()) {
				sizeRow++;
			}
		}
		return sizeRow == rows;
	}

	/*
	 * testColumnOrder It is a method to test the order of the column
	 */
	public void testColumnOrder(String tableName, Row r) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		if (r.getLastCellNum() != columns.size()) {
			// TODO message
			markErroneousCell(r.getCell(0), "size of cell and size of column not equals", r);
		}
		for (int i = 0; i < r.getLastCellNum(); i++) {
			Cell cell = r.getCell(i);
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

	public void globalTest(String tableName, Row r) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			String message = "";
			Column column = columns.get(i);
			Cell cell = r.getCell(i);
			if (cell != null) {
				message = message + testType(column, cell, i, r);
				message = message + testSize(column, cell, i, r);
				if (message.equals("")) {
					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							CellStyle cellStyle = workbook.createCellStyle();
							CreationHelper createHelper = workbook.getCreationHelper();
							cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyyMMdd"));
							cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
							// cell.setCellValue(date);
							cell.setCellStyle(cellStyle);
							// binds the style you need to the cell.
							try {
								FileOutputStream out = new FileOutputStream(fileOut);
								workbook.write(out);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							cell.setCellStyle(okCellStyle);
						}
					} else {
						cell.setCellStyle(okCellStyle);
					}
				} else {
					markErroneousCell(cell, message, r);
				}
			}
		}
	}

	private String testSize(Column column, Cell cell, int numCell, Row r) {
		// TODO Auto-generated method stub
		CellType type = cell.getCellTypeEnum();
		int integerSize = 0;
		int decimalSize = 0;
		String message = "";
		if (type == CellType.STRING) {
			integerSize = cell.getStringCellValue().length();
			if (integerSize > column.getPrecision()) {
				// TODO message
				message = "The size of the cell is a false.";
				// markErroneousCell(cell,message , r);
			}
		} else if (type == CellType.NUMERIC) {
			double d = cell.getNumericCellValue();
			String text = Double.toString(Math.abs(d));
			integerSize = text.indexOf('.');
			String.valueOf(cell.getNumericCellValue()).length();
			decimalSize = text.length() - integerSize - 1;
			if ((decimalSize == 1) && (text.charAt(integerSize + 1) == '0') && (integerSize > column.getPrecision())) {
				// TODO message
				message = "The size of the cell is a false.";
				// markErroneousCell(cell,message , r);
			} else if ((integerSize > column.getPrecision())
					|| (decimalSize > column.getScale()) && (text.charAt(integerSize + 1) != '0')) {
				// TODO message
				message = "The size of the cell is a false.";
				// markErroneousCell(cell,message , r);
			}
		}
		return message;
	}

	private String testType(Column column, Cell cell, int numCell, Row r) {
		String message = "";
		// TODO Auto-generated method stub
		CellType type = cell.getCellTypeEnum();
		String typeTable = column.getColumnType();

		if ((typeTable.equals("varchar (" + column.getPrecision() + " )")) && (type != CellType.STRING)) {
			// cellTypeTable = CellType.STRING;
			message = "The type for cell is false.";
			// markErroneousCell(cell,message , r);
		} else if ((typeTable.equals("int4 (" + column.getPrecision() + ")")) && (!type.equals(CellType.NUMERIC))) {
			// cellTypeTable = CellType.NUMERIC;
			message = "The type for cell is false.";
			// markErroneousCell(cell,message , r);
		} else if ((typeTable.equals("numeric (" + column.getPrecision() + "," + column.getScale() + ")"))
				&& (!type.equals(CellType.NUMERIC))) {
			message = "The type for cell is false.";
		} else if ((typeTable.equals("boolean")) && (type != CellType.BOOLEAN)) {
			message = "The type for cell is false.";
			// markErroneousCell(cell,message , r);
		}
		return message;
	}

}
