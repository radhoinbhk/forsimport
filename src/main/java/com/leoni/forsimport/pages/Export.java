package com.leoni.forsimport.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.Column;
import com.leoni.forsimport.model.Table;
import com.leoni.forsimport.services.ExcelStreamResponse;
import com.leoni.forsimport.services.TabNames;

public class Export {
	private static final Logger LOG = Logger.getLogger(Export.class);

	@Property
	@Persist
	private String tableName;

	/**
	 * 
	 */
	@Inject
    private TabNames tableNames;

    // The code
    List<String> onProvideCompletionsFromtableName(String partial) {
    	List<String> matches = new ArrayList<String>();
        partial = partial.toUpperCase();

        for (String countryName : tableNames.getTableNames()) {
            if (countryName.toUpperCase().contains(partial)) {
                matches.add(countryName);
            }
        }

        return matches;
    }
    
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
		CellStyle colorCellStyle = wb.createCellStyle();
		colorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		colorCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		for (int i = 0; i < columns.size(); i++) {
			column = columns.get(i);
			// 4. COLUMN_NAME
			Cell columnName = rowName.createCell(i);
			columnName.setCellValue(column.getColumnName());
			columnName.setCellStyle(colorCellStyle);
			// 5. COLUMN_Type
			Cell colulnType = rowType.createCell(i);
			colulnType.setCellValue(column.getColumnType());
		}
		String tempFolder = System.getProperty("java.io.tmpdir");
		LOG.info("Temp folder :" + tempFolder);
		try {
			File file = new File(
					tempFolder + File.separator + table.getTableName() + "_" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			// Thread.sleep(10000);
			return new ExcelStreamResponse(new FileInputStream(file), tableName);
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
}
