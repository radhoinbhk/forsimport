package com.leoni.forsimport.pages;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Contact {

	public static void main(String[] args) {
		Error404 export = new Error404();
		Table table = new Table();
		table.setColumns(export.getStructurTable());

		ArrayList<Column> columns = new ArrayList<Column>();
		columns = table.getColumns();

		// 1. Créer un Document vide
		XSSFWorkbook wb = new XSSFWorkbook();
		// 2. Créer une Feuille de calcul vide
		Sheet feuille = wb.createSheet("Sheet");
		feuille.setDefaultColumnWidth(15);
		// 3. Créer une ligne et mettre qlq chose dedans
		Row row = feuille.createRow((short) 0);
		// 4. COLUMN_NAME
		Cell colulnName = row.createCell(0);
		colulnName.setCellValue("COLUMN_NAME");
		// 5. DATA_TYPE
		Cell dataType = row.createCell(1);
		dataType.setCellValue("DATA_TYPE");
		// 6. PRIMARY_KEY
		Cell primaryKey = row.createCell(2);
		primaryKey.setCellValue("PRIMARY_KEY");
		// 7. NULLABELE
		Cell nullabele = row.createCell(3);
		nullabele.setCellValue("NULLABELE");
		// 8. DEFAULT
		Cell dfault = row.createCell(4);
		dfault.setCellValue("DEFAULT");
		// 9. AUTOINCREMENT
		Cell autoIncrement = row.createCell(5);
		autoIncrement.setCellValue("AUTOINCREMENT");
		// 10. COMPUTED
		Cell computed = row.createCell(6);
		computed.setCellValue("COMPUTED");
		// 11. REMARKS
		Cell remarks = row.createCell(7);
		remarks.setCellValue("REMARKS");
		// 12. JDBC Type
		Cell JdbcType = row.createCell(8);
		JdbcType.setCellValue("JDBC Type");
		// 13. JDBC Type
		Cell position = row.createCell(9);
		position.setCellValue("POSITION");

		Column column = new Column();
		for (int i = 1; i < columns.size() + 1; i++) {
			column = columns.get(i - 1);
			// 3. Créer une ligne et mettre qlq chose dedans
			row = feuille.createRow((short) i);
			// 4. COLUMN_NAME
			colulnName = row.createCell(0);
			colulnName.setCellValue(column.getColumnName());
			// 5. DATA_TYPE
			dataType = row.createCell(1);
			dataType.setCellValue(column.getColumnType());
			// 6. PRIMARY_KEY
			primaryKey = row.createCell(2);
			primaryKey.setCellValue(column.isPrimaryKey());
			// 7. NULLABELE
			nullabele = row.createCell(3);
			nullabele.setCellValue(column.isNullable());
			// 8. DEFAULT
			dfault = row.createCell(4);
			dfault.setCellValue(column.getDefaultValue());
			// 9. AUTOINCREMENT
			autoIncrement = row.createCell(5);
			autoIncrement.setCellValue(column.isAutoIncrement());
			// 10. COMPUTED
			computed = row.createCell(6);
			computed.setCellValue("NO");
			// 11. REMARKS
			remarks = row.createCell(7);
			remarks.setCellValue("");
			// 12. JDBC Type
			JdbcType = row.createCell(8);
			JdbcType.setCellValue(column.getColumnTypeJdbc());

			// 13. JDBC Type
			position = row.createCell(9);
			position.setCellValue(column.getPositioncolumn());

			/*
			 * //Ajouter d'autre cellule avec différents type
			 * introw.createCell(1).setCellValue(3);
			 * charrow.createCell(2).setCellValue('c');
			 * Stringrow.createCell(3).setCellValue("chaine");
			 * booleanrow.createCell(4).setCellValue(false);
			 */
			try {
				FileOutputStream fileOut = new FileOutputStream(
						"C:/Users/HP/workspacePFE/forsimport/src/main/webapp/Excel/eport.xlsx");
				wb.write(fileOut);
				fileOut.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("export table BUILD SUCCESS");
		System.out.println(table.getTableName());
	}
}
