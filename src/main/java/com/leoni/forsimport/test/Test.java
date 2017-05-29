////package com.leoni.forsimport.test;
////
////import java.io.File;
////import java.io.FileNotFoundException;
////import java.io.FileOutputStream;
////import java.io.IOException;
////
////import org.apache.poi.ss.usermodel.Cell;
////import org.apache.poi.ss.usermodel.CellStyle;
////import org.apache.poi.ss.usermodel.ClientAnchor;
////import org.apache.poi.ss.usermodel.Comment;
////import org.apache.poi.ss.usermodel.CreationHelper;
////import org.apache.poi.ss.usermodel.Drawing;
////import org.apache.poi.ss.usermodel.FillPatternType;
////import org.apache.poi.ss.usermodel.IndexedColors;
////import org.apache.poi.ss.usermodel.RichTextString;
////import org.apache.poi.ss.usermodel.Row;
////import org.apache.poi.ss.usermodel.Sheet;
////import org.apache.poi.ss.usermodel.Workbook;
////import org.apache.poi.xssf.usermodel.XSSFWorkbook;
////
////public class Test {
////
////	private CellStyle errorCellStyle;
////
////	private CellStyle okCellStyle;
////	private Comment comment;
////	private Drawing drawing;
////	private ClientAnchor anchor;
////	private CreationHelper factory;
////	public static void main(String[] args) {
////		String dateString = /*cell.getDateCellValue().toString()*/"2016/02/03";
////		String[] tab = dateString.split("/");
////		System.out.println(String.format("%s%s%s", tab[0], tab[1], tab[2]));
////		Test test = new Test();
////		test.run();
////		System.out.println("Done");
////	}
////
////	private void date() {
////		
////	}
////	private void run() {
////
////		Workbook workbook = null;
////		try {
////			workbook = new XSSFWorkbook("d:/test.xlsx");
////			initCellStyles(workbook);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		Sheet sheet = workbook.getSheetAt(0);
////		initCellComment(workbook,sheet);
////		int i = 0;
////		for (Row r : sheet) {
////			for (Cell cell : r) {
////				markErroneousCell(cell, "", i,r);
////			}
//////				CreationHelper factory = workbook.getCreationHelper();
//////				Drawing drawing = sheet.createDrawingPatriarch();
//////				ClientAnchor anchor = factory.createClientAnchor();
//////				anchor.setCol1(r.getCell(0).getColumnIndex());
//////				anchor.setCol2(r.getCell(0).getColumnIndex() + 1);
//////				anchor.setRow1(r.getRowNum());
//////				anchor.setRow2(r.getRowNum() + 3);
//////				comment = drawing.createCellComment(anchor);
//////			    RichTextString str = factory.createRichTextString("Hello, World!");
//////			    comment.setString(str);
//////			    r.getCell(0).setCellComment(comment);
////			i++;
////		}
////		try {
////			File filetemp = new File("d:/" + System.currentTimeMillis() + ".xlsx");
////			FileOutputStream excelFileOut = new FileOutputStream(filetemp);
////			workbook.write(excelFileOut);
////			excelFileOut.close();
////
////		} catch (FileNotFoundException e) {
////			System.out.println("File not found");
////		} catch (IOException e) {
////			System.out.println("Unknown error");
////		}
////	}
////	private void initCellComment(Workbook wb, Sheet sheet){
////		factory = wb.getCreationHelper();
////		drawing = sheet.createDrawingPatriarch();
////		anchor = factory.createClientAnchor();
////	}
////
////	private void initCellStyles(Workbook wb) {
////		errorCellStyle = wb.createCellStyle();
////		errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
////		errorCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
////		okCellStyle = wb.createCellStyle();
////		okCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
////		okCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
////	}
////	
////	
////	private void markErroneousCell(Cell cell, String message, int i,Row r) {
////		if (i % 2 == 0) {
////			cell.setCellStyle(okCellStyle);
////		} else {
////			cell.setCellStyle(errorCellStyle);
////			anchor.setCol1(cell.getColumnIndex());
////			anchor.setCol2(cell.getColumnIndex() + 1);
////			anchor.setRow1(r.getRowNum());
////			anchor.setRow2(r.getRowNum() + 3);
////			Comment comment = drawing.createCellComment(anchor);
////		    RichTextString str = factory.createRichTextString(message);
////		    comment.setString(str);
////		    comment.setAuthor("Apache POI");
////		    cell.setCellComment(comment);
////		}
////	}
////
////}
//package com.leoni.forsimport.model;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import org.apache.catalina.connector.OutputBuffer;
//import org.apache.log4j.Logger;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.ClientAnchor;
//import org.apache.poi.ss.usermodel.Comment;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.apache.poi.ss.usermodel.Drawing;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.RichTextString;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.google.protobuf.ByteString.Output;
//import com.leoni.forsimport.dao.TableDAO;
//import com.leoni.forsimport.pages.Import;
//import com.leoni.forsimport.services.ExcelStreamResponse;
//import com.monitorjbl.xlsx.StreamingReader;
//
//public class VerifyExcelFile {
//
//	private static final Logger LOG = Logger.getLogger(Import.class);
//	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
//	private static CellStyle errorCellStyle;
//	private static CellStyle okCellStyle;
//	private static Drawing drawing;
//	private static ClientAnchor anchor;
//	private static CreationHelper factory;
//	private static Workbook workbook = null;
//	/*
//	 * getExcelFileverify it is a method return the EXCEL file verify
//	 */
//
//	// public ExcelStreamResponse getExcelFileverify(File file, String fileName)
//	// {
//	public static void main(String[] args) {
//		String fileName = "target_table";
//		// TODO name for table
//		// int index = fileName.indexOf(".");
//		// String tableName = fileName.substring(0, index);
//		// LOG.info("444444444444***********" + tableName);
//		// TODO Auto-generated method stub
//		FileInputStream inputStream = null;
//		try {
//			inputStream = new FileInputStream("D:\target_table.xlsx");
//			workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(inputStream);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Sheet sheet = workbook.getSheetAt(0);
//		errorCellStyle = workbook.createCellStyle();
//		errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		errorCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
//		okCellStyle = workbook.createCellStyle();
//		okCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		okCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//		factory = workbook.getCreationHelper();
//		drawing = sheet.createDrawingPatriarch();
//		anchor = factory.createClientAnchor();
//		for (Row r : sheet) {
//			ArrayList<Cell> cells = new ArrayList<Cell>();
//			// for (Cell cell : r) {
//			// cells.add(cell);
//			// }
//			if (r.getRowNum() == 0) {
//				testColumnOrder(fileName, r);
//			}
//			if (r.getRowNum() != 0 && cells.size() > 0) {
//				globalTest(fileName, r);
//			}
//		}
//
//		FileOutputStream out = null;
//
//		File fileOut = new File(fileName);
//		try {
//			out = new FileOutputStream(fileOut);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			workbook.write(out);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		String tempFolder = System.getProperty("java.io.tmpdir");
//		try {
//			File filetemp = new File(
//					tempFolder + File.separator + fileName + "_" + System.currentTimeMillis() + ".xlsx");
//			FileOutputStream excelFileOut = new FileOutputStream(filetemp);
//			workbook.write(excelFileOut);
//			excelFileOut.close();
//			// Thread.sleep(10000);
//			// return new ExcelStreamResponse(new FileInputStream(filetemp),
//			// fileName);
//			// Writer outz = new BufferedWriter(new
//			// OutputStreamWriter(System.out));
//		} catch (FileNotFoundException e) {
//			LOG.error("File not found", e);
//			return;
//		} catch (IOException e) {
//			LOG.error("Unknown error", e);
//			return;
//		} /*
//			 * catch (InterruptedException e) { LOG.error("Unknown error", e);
//			 * return null; }
//			 */
//	}
//
//	/*
//	 * testColumnOrder It is a method to test the order of the column
//	 */
//	public static void testColumnOrder(String tableName, Row r) {
//		// TODO Auto-generated method stub
//		TableDAO dao = new TableDAO();
//		Table table = dao.getTableStructure(tableName);
//		ArrayList<Column> columns = table.getColumns();
//		if (r.getLastCellNum() != columns.size()) {
//			// TODO message
//			LOG.info("size of cell and size of column not equals");
//			LOG.info("cells.size() " + r.getLastCellNum());
//			LOG.info("columns.size()" + columns.size());
//			markErroneousCell(r.getCell(0), "size of cell and size of column not equals", r);
//		}
//		for (int i = 0; i < r.getLastCellNum(); i++) {
//			Cell cell = r.getCell(i);
//			int type = cell.getCellType();
//			if (type != CellType.STRING.getCode()) {
//				// TODO message
//				LOG.info("The cell is not String");
//				markErroneousCell(cell, "The cell is not String", r);
//			} else if (!cell.getStringCellValue().equals(columns.get(i).getColumnName())) {
//				// TODO message
//				LOG.info("false name cell");
//				markErroneousCell(cell, "false name cell", r);
//			}
//		}
//
//	}
//
//	/**
//	 * marks the given cell as erroneous and creates a comment with an error
//	 * message.
//	 * 
//	 * @param cell
//	 * @param message
//	 * @param r
//	 */
//	private static void markErroneousCell(Cell cell, String message, Row r) {
//		cell.setCellStyle(errorCellStyle);
//		// TODO the problem for comment where cell >>>
//		anchor.setCol1(cell.getColumnIndex());
//		anchor.setCol2(cell.getColumnIndex() + 2);
//		anchor.setRow1(r.getRowNum());
//		anchor.setRow2(r.getRowNum() + 3);
//		Comment comment = drawing.createCellComment(anchor);
//		RichTextString str = factory.createRichTextString(message);
//		comment.setString(str);
//		comment.setAuthor("Apache POI");
//		cell.setCellComment(comment);
//	}
//
//	/*
//	 * globalTest This is a method to test all the errors found in the column
//	 */
//
//	public static void globalTest(String tableName, Row r) {
//		// TODO Auto-generated method stub
//		TableDAO dao = new TableDAO();
//		Table table = dao.getTableStructure(tableName);
//		ArrayList<Column> columns = table.getColumns();
//		for (int i = 0; i < columns.size(); i++) {
//			String message = "";
//			Column column = columns.get(i);
//			Cell cell = r.getCell(i);
//			message = testType(column, cell, i, r);
//			message = message + testSize(column, cell, i, r);
//			if (message.equals("")) {
//				cell.setCellStyle(okCellStyle);
//			} else {
//				markErroneousCell(cell, message, r);
//			}
//		}
//	}
//
//	private static String testSize(Column column, Cell cell, int numCell, Row r) {
//		// TODO Auto-generated method stub
//		CellType type = cell.getCellTypeEnum();
//		int integerSize = 0;
//		int decimalSize = 0;
//		String message = "";
//		if (type == CellType.STRING) {
//			integerSize = cell.getStringCellValue().length();
//			if (integerSize > column.getPrecision()) {
//				// TODO message
//				message = "The size of the cell is a false.";
//				// markErroneousCell(cell,message , r);
//			}
//		} else if (type == CellType.NUMERIC) {
//			double d = cell.getNumericCellValue();
//			String text = Double.toString(Math.abs(d));
//			integerSize = text.indexOf('.');
//			String.valueOf(cell.getNumericCellValue()).length();
//			decimalSize = text.length() - integerSize - 1;
//			if ((decimalSize == 1) && (text.charAt(integerSize + 1) == '0') && (integerSize > column.getPrecision())) {
//				// TODO message
//				message = "The size of the cell is a false.";
//				// markErroneousCell(cell,message , r);
//			} else if ((integerSize > column.getPrecision()) || (decimalSize > column.getScale())) {
//				// TODO message
//				message = "The size of the cell is a false.";
//				// markErroneousCell(cell,message , r);
//			}
//		}
//		return message;
//	}
//
//	private static String testType(Column column, Cell cell, int numCell, Row r) {
//		String message = "";
//		// TODO Auto-generated method stub
//		CellType type = cell.getCellTypeEnum();
//		String typeTable = column.columnType;
//		LOG.info("test type " + (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC));
//		LOG.info("type " + cell.getCellType());
//		LOG.info("CELL_TYPE_NUMERIC " + HSSFCell.CELL_TYPE_NUMERIC);
//		LOG.info("cell " + cell);
//		LOG.info("column " + column);
//		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//			if (HSSFDateUtil.isCellDateFormatted(cell)) {
//				LOG.info("HSSFDateUtil.isCellDateFormatted(cell)" + HSSFDateUtil.isCellDateFormatted(cell));
//				Date date = cell.getDateCellValue()/* 2016/02/03" */;
//				CellStyle cellStyle = workbook.createCellStyle();
//				CreationHelper createHelper = workbook.getCreationHelper();
//				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyyMMdd"));
//				cell.setCellValue(date);
//				cell.setCellStyle(cellStyle);
//				LOG.info("date " + date);
//				LOG.info("cell  " + cell);
//				LOG.info("cellStyle  " + cellStyle);
//				LOG.info("cell.getCellStyle()  " + cell.getCellStyle());
//				LOG.info("**********");
//			}
//		}
//		if ((typeTable.equals("varchar")) && (type != CellType.STRING)) {
//			// cellTypeTable = CellType.STRING;
//			message = "The type for cell is false.";
//			// markErroneousCell(cell,message , r);
//		} else if ((typeTable.equals("int4")) && (type != CellType.NUMERIC)) {
//			// cellTypeTable = CellType.NUMERIC;
//			message = "The type for cell is false.";
//			// markErroneousCell(cell,message , r);
//		} else if ((typeTable.equals("boolean")) && (type != CellType.BOOLEAN)) {
//			message = "The type for cell is false.";
//			// markErroneousCell(cell,message , r);
//		}
//		return message;
//	}
//
//}

package com.leoni.forsimport.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.Column;
import com.leoni.forsimport.model.Table;
import com.leoni.forsimport.pages.Import;
import com.monitorjbl.xlsx.StreamingReader;

public class Test {

	private static final Logger LOG = Logger.getLogger(Import.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	private static CellStyle errorCellStyle;
	private static CellStyle okCellStyle;
	private static Drawing drawing;
	private static ClientAnchor anchor;
	private static CreationHelper factory;
	private static Workbook workbook = null;
	/*
	 * getExcelFileverify it is a method return the EXCEL file verify
	 */

//	public ExcelStreamResponse getExcelFileverify(File file, String fileName) {
	public static void main(String[] args) {
		String fileName = "target_table";
		// TODO name for table
		// int index = fileName.indexOf(".");
		// String tableName = fileName.substring(0, index);
		// LOG.info("444444444444***********" + tableName);
		// TODO Auto-generated method stub
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("D:/target_table.xlsx");
			workbook = StreamingReader.builder().rowCacheSize(100)
					.bufferSize(4096) 
					.open(inputStream); 
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
//			for (Cell cell : r) {
//				cells.add(cell);
//			}
			if (r.getRowNum() == 0) {
				testColumnOrder(fileName, r);
			}
			if (r.getRowNum() != 0 && cells.size() > 0) {
				globalTest(fileName, r);
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
		try {
			File filetemp = new File(
					tempFolder + File.separator + fileName + "_" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream excelFileOut = new FileOutputStream(filetemp);
			workbook.write(excelFileOut);
			excelFileOut.close();
			// Thread.sleep(10000);
			//return new ExcelStreamResponse(new FileInputStream(filetemp), fileName);
			Writer out1 = new BufferedWriter(new OutputStreamWriter(System.out));
		} catch (FileNotFoundException e) {
			LOG.error("File not found", e);
			return;
		} catch (IOException e) {
			LOG.error("Unknown error", e);
			return;
		} /*
			 * catch (InterruptedException e) { LOG.error("Unknown error", e);
			 * return null; }
			 */
	}

	/*
	 * testColumnOrder It is a method to test the order of the column
	 */
	public static void testColumnOrder(String tableName, Row r) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		if (r.getLastCellNum() != columns.size()) {
			// TODO message
			LOG.info("size of cell and size of column not equals");
			LOG.info("cells.size() " + r.getLastCellNum());
			LOG.info("columns.size()" + columns.size());
			markErroneousCell(r.getCell(0), "size of cell and size of column not equals", r);
		}
		for (int i = 0; i < r.getLastCellNum(); i++) {
			Cell cell = r.getCell(i);
			int type = cell.getCellType();
			if (type != CellType.STRING.getCode()) {
				// TODO message
				LOG.info("The cell is not String");
				markErroneousCell(cell, "The cell is not String", r);
			} else if (!cell.getStringCellValue().equals(columns.get(i).getColumnName())) {
				// TODO message
				LOG.info("false name cell");
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
	private static void markErroneousCell(Cell cell, String message, Row r) {
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

	public static void globalTest(String tableName, Row r) {
		// TODO Auto-generated method stub
		TableDAO dao = new TableDAO();
		Table table = dao.getTableStructure(tableName);
		ArrayList<Column> columns = table.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			String message = "";
			Column column = columns.get(i);
			Cell cell = r.getCell(i);
			message = testType(column, cell, i, r);
			message = message + testSize(column, cell, i, r);
			if (message.equals("")) {
				cell.setCellStyle(okCellStyle);
			} else {
				markErroneousCell(cell, message, r);
			}
		}
	}

	private static String testSize(Column column, Cell cell, int numCell, Row r) {
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
			} else if ((integerSize > column.getPrecision()) || (decimalSize > column.getScale())) {
				// TODO message
				message = "The size of the cell is a false.";
				// markErroneousCell(cell,message , r);
			}
		}
		return message;
	}

	private static String testType(Column column, Cell cell, int numCell, Row r) {
		String message = "";
		// TODO Auto-generated method stub
		CellType type = cell.getCellTypeEnum();
		String typeTable = column.getColumnType();
		LOG.info("test type " + (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC));
		LOG.info("type " + cell.getCellType());
		LOG.info("CELL_TYPE_NUMERIC " + HSSFCell.CELL_TYPE_NUMERIC);
		LOG.info("cell " + cell);
		LOG.info("column " + column);
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				LOG.info("HSSFDateUtil.isCellDateFormatted(cell)" + HSSFDateUtil.isCellDateFormatted(cell));
				Date date = cell.getDateCellValue()/* 2016/02/03" */;
				CellStyle cellStyle = workbook.createCellStyle();
				CreationHelper createHelper = workbook.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyyMMdd"));
				cell.setCellValue(date);
				cell.setCellStyle(cellStyle);
				LOG.info("date " + date);
				LOG.info("cell  " + cell);
				LOG.info("cellStyle  " + cellStyle);
				LOG.info("cell.getCellStyle()  " + cell.getCellStyle());
				LOG.info("**********");
			}
		}
		if ((typeTable.equals("varchar")) && (type != CellType.STRING)) {
			// cellTypeTable = CellType.STRING;
			message = "The type for cell is false.";
			// markErroneousCell(cell,message , r);
		} else if ((typeTable.equals("int4")) && (type != CellType.NUMERIC)) {
			// cellTypeTable = CellType.NUMERIC;
			message = "The type for cell is false.";
			// markErroneousCell(cell,message , r);
		} else if ((typeTable.equals("boolean")) && (type != CellType.BOOLEAN)) {
			message = "The type for cell is false.";
			// markErroneousCell(cell,message , r);
		}
		return message;
	}

}
