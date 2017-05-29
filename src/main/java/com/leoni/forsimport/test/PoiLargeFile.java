package com.leoni.forsimport.test;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PoiLargeFile {
//
//	public static void main(String[] args) throws Exception {
//		System.out.println(new Date());
//		File file = new File("D:\\target_table.xlsx");
//		// InputStream inp = new FileInputStream("");
//		// InputStream inp = new FileInputStream("workbook.xlsx");
//
//		Workbook wb = WorkbookFactory.create(file);
//		Sheet sheet = wb.getSheetAt(0);
//		int rows = sheet.getLastRowNum();
//		for (int i = 0; i < rows; i++) {
//			Row row = sheet.getRow(i);
//			if (row != null) {
//				for (int j = 0; j < row.getLastCellNum(); j++) {
//					Cell cell = row.getCell(j);
//					if (cell != null) {
//						switch (cell.getCellType()) {
//						case Cell.CELL_TYPE_NUMERIC:
//							System.out.println("[" + i + "," + j + "]:" + cell.getNumericCellValue());
//							break;
//						case Cell.CELL_TYPE_STRING:
//							System.out.println("[" + i + "," + j + "]:" + cell.getStringCellValue());
//							break;
//						case Cell.CELL_TYPE_BOOLEAN:
//							System.out.println("[" + i + "," + j + "]:" + cell.getBooleanCellValue());
//							break;
//						case Cell.CELL_TYPE_BLANK:
//							System.out.println("[" + i + "," + j + "]:" + cell.getStringCellValue());
//							break;
//						}
//						
//					}
//				}
//			}
//		}
//		System.out.println(new Date());
//
//		// wb.close();
//	}
	public static void main(String[] args) {
		System.out.println();
	}
}
