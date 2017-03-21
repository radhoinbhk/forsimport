package com.leoni.forsimport.pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class About {

	/**
	 * Méthode de récupération structure de table sous forme de fichier Excel
	 */

	public static void getExcel(Table table) {

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
			// 5. COLUMN_Type
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
	public static void main(String[] args) {

		// initialize File object
		File file = new File("export.xslx");
		boolean result;

		// check if file exists
		result = file.exists();
		try {
			if (result) {

				// file exists
				System.out.println(file.getCanonicalPath() + " exists");
				// System.out.println(file.getAbsolutePath()+ " exists");
				// System.out.println(file.getPath()+ " exists");
			} else {

				// file does not exist
				System.out.println(file.getCanonicalPath() + " does not exists");

			}

			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);

			for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						XSSFCell cell = row.getCell(c);
						if (cell != null) {
							CellType type = cell.getCellTypeEnum();
							if (type == CellType.STRING) {
								System.out.println("string" + cell.getStringCellValue());
							} else if (type == CellType.NUMERIC) {
								System.out.println("numeric" + cell.getNumericCellValue());
							} else if (type == CellType.BOOLEAN) {
								System.out.println("boolean" + cell.getBooleanCellValue());
							}

						}
					}
				}
			}

		} catch (Exception e) {
			System.err.println("Things went wrong: " + e.getMessage());
			e.printStackTrace();

		}

	}
}