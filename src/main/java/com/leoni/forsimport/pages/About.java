package com.leoni.forsimport.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

public class About {

	/**
	 * method create the EXEL file for structure table
	 */

	public static void setExcel(Table table) {

		ArrayList<Column> columns = new ArrayList<Column>();
		columns = table.getColumns();

		// 1. Créer un Document vide
		XSSFWorkbook wb = new XSSFWorkbook();
		// 2. Créer une Feuille de calcul vide
		Sheet feuille = wb.createSheet("Sheet");
		feuille.setDefaultColumnWidth(15);
		// 3. Créer une ligne et mettre qlq chose dedans
		Row rowName = feuille.createRow((short) 0);
		Row rowType = feuille.createRow((short) 1);
		Column column = new Column();
		for (int i = 0; i < columns.size(); i++) {
			column = columns.get(i);
			// 4. COLUMN_NAME
			Cell colulnName = rowName.createCell(i);
			colulnName.setCellValue(column.getColumnName());
			// 4. COLUMN_Type
			Cell colulnType = rowType.createCell(i);
			colulnType.setCellValue(column.getColumnType());
			try {
				FileOutputStream fileOut = new FileOutputStream(table.getTableName() + ".xlsx");
				wb.write(fileOut);
				fileOut.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Excel created");
	}

	/**
	 * read the Excel file
	 */
	// public static Table getExcel() {
	public static void main(String[] args) {

		try {
			FileInputStream fs = new FileInputStream(
					new File("C:/Users/HP/workspacePFE/forsimport/src/main/webapp/Excel/the Excel readed/01.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sheet = wb.getSheetAt(0);

			for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						XSSFCell cell = row.getCell(c);
						if (cell != null) {
							CellType type = cell.getCellTypeEnum();
							if (type == CellType.STRING) {
								System.out.println("string ***" + cell.getStringCellValue());
							} else if (type == CellType.NUMERIC) {
								System.out.println("numeric" + cell.getNumericCellValue());
							} else if (type == CellType.BOOLEAN) {
								System.out.println("boolean" + cell.getBooleanCellValue());
							}

						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return null;

	}
}
