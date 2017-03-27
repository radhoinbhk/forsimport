package com.leoni.forsimport.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.leoni.forsimport.model.Error;
import com.leoni.forsimport.model.verifyExcelFile;
import com.leoni.forsimport.services.ExcelStreamResponse;
import com.monitorjbl.xlsx.StreamingReader;

/**
 * Upload Excel file.
 */
public class Import {
	private static final Logger LOG = Logger.getLogger(Import.class);

	@Property
	private UploadedFile file;
	Error error = new Error();
	private ArrayList<Error> errors = new ArrayList();

	/**
	 * @return
	 * 
	 */
	@SuppressWarnings("deprecation")
	public ExcelStreamResponse onSelectedFromUpload() {
		LOG.info("Starting");
		String temp_folder = System.getProperty("java.io.tmpdir");
		// String fileSeparator = File.separator;
		LOG.info("*************************************Temp folder :" + temp_folder);
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
		verifyExcelFile verifyExcelFile = new verifyExcelFile();
		String fileName = "target_table";
		for (Row r : sheet) {
			ArrayList<Cell> cells = new ArrayList<Cell>();
			for (Cell cell : r) {
				cells.add(cell);
			}
			if (r.getRowNum() == 0) {
				errors = verifyExcelFile.testColumnOrder(cells, fileName);
				CellStyle backgroundStyle = workbook.createCellStyle();
				backgroundStyle.setRightBorderColor(IndexedColors.RED.getIndex());
				for (int i = 0; i < errors.size(); i++) {
					error = errors.get(i);
					int NumCell = error.getNumCellError();
					r.getCell(NumCell).setCellStyle(backgroundStyle);
				}
			} else {
				
				/**
				****test type
				*/
				errors = verifyExcelFile.testType(cells, fileName);
				CellStyle backgroundStyle = workbook.createCellStyle();
				backgroundStyle.setRightBorderColor(IndexedColors.RED.getIndex());
				for (int i = 0; i < errors.size(); i++) {
					error = errors.get(i);
					int NumCell = error.getNumCellError();
					r.getCell(NumCell).setCellStyle(backgroundStyle);
				}
					/**
					****test size
					*/
					errors = verifyExcelFile.testSize(cells, fileName);
					for (int i = 0; i < errors.size(); i++) {
						error = errors.get(i);
						int NumCell = error.getNumCellError();
						r.getCell(NumCell).setCellStyle(backgroundStyle);
				}

		}
		}
		FileOutputStream out = null;

		File file1 = new File(file.getFileName());
		try {
			out = new FileOutputStream(file1);
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
		LOG.info("Temp folder :" + tempFolder);
		try {
			File file = new File(tempFolder + File.separator + fileName + "_" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			fileOut.close();
			// Thread.sleep(10000);
			return new ExcelStreamResponse(new FileInputStream(file), fileName);
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

