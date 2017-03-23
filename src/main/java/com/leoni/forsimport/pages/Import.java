package com.leoni.forsimport.pages;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.monitorjbl.xlsx.StreamingReader;

/**
 * Upload Excel file.
 */
public class Import {
	private static final Logger LOG = Logger.getLogger(Import.class);

	@Property
	private UploadedFile file;

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void onSelectedFromUpload() {
		LOG.info("Starting");
		String temp_folder = System.getProperty("java.io.tmpdir");
		// String fileSeparator = File.separator;
		LOG.info("Temp folder :" + temp_folder);
		// File copied = new File(temp_folder + file.getFileName());
		//
		// file.write(copied);
		//
		// LOG.info("File copied to temp folder");

		Workbook workbook = StreamingReader.builder().rowCacheSize(100) // number
																		// of
																		// rows
																		// to
																		// keep
																		// in
																		// memory
																		// (defaults
																		// to
																		// 10)
				.bufferSize(4096) // buffer size to use when reading InputStream
									// to file (defaults to 1024)
				.open(file.getStream()); // InputStream or File for XLSX file
											// (required)
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println(sheet.getSheetName());
		for (Row r : sheet) {
			for (Cell cell : r) {
				int type = cell.getCellType();
				if (type == CellType.STRING.getCode()) {
					LOG.info("string" + cell.getStringCellValue());
				} else if (type == CellType.NUMERIC.getCode()) {
					LOG.info("numeric" + cell.getNumericCellValue());
				} else if (type == CellType.BOOLEAN.getCode()) {
					LOG.info("boolean" + cell.getBooleanCellValue());
				}
			}
			// TableDAO dao = new TableDAO();
			// dao.getTableStructure("");

		}
	}
}
