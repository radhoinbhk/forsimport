package com.leoni.forsimport.pages;

import java.io.File;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class About {

	

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