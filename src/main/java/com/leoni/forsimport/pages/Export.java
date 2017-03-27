package com.leoni.forsimport.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.Column;
import com.leoni.forsimport.model.Table;
import com.leoni.forsimport.services.ExcelStreamResponse;

public class Export {
	private static final Logger LOG = Logger.getLogger(Export.class);
	
	@Property
	@Persist
	private String tableName;
	/**
	 * 
	 */
	
	public StreamResponse onSelectedFromExport() {
		LOG.info("Start");
		TableDAO dao = new TableDAO();
		return generateExcel(dao.getTableStructure(tableName));
	}

	
	/**
	 * Méthode de récupération structure de table sous forme de fichier Excel
	 */

	public StreamResponse generateExcel(Table table) {
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
			Cell columnName = rowName.createCell(i);
			columnName.setCellValue(column.getColumnName());
			// 5. COLUMN_Type
			Cell colulnType = rowType.createCell(i);
			colulnType.setCellValue(column.getColumnType());
		}
		String tempFolder = System.getProperty("java.io.tmpdir");
		LOG.info("Temp folder :" + tempFolder);
		try {
			File file = new File(tempFolder + File.separator + table.getTableName() + "_" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
//			Thread.sleep(10000);
			return new ExcelStreamResponse(new FileInputStream(file), tableName);
		} catch (FileNotFoundException e) {
			LOG.error("File not found", e);
			return null;
		} catch (IOException e) {
			LOG.error("Unknown error", e);
			return null;
		} /*catch (InterruptedException e) {
			LOG.error("Unknown error", e);
			return null;
		}*/

	}
	

}
