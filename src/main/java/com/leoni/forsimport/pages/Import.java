package com.leoni.forsimport.pages;

import java.io.File;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.leoni.forsimport.model.Error;
import com.leoni.forsimport.model.VerifyExcelFile;
import com.leoni.forsimport.services.ExcelStreamResponse;
import com.monitorjbl.xlsx.StreamingReader;

/**
 * Upload Excel file.
 */
public class Import {
	private static final Logger LOG = Logger.getLogger(Import.class);

	@Property
	@Persist
	private String tableName;
	
	@Property
	private UploadedFile file;
	Error error = new Error();

	/**
	 * @return
	 * 
	 */
	public ExcelStreamResponse onSelectedFromUpload() {
		LOG.info("Starting");
		String temp_folder = System.getProperty("java.io.tmpdir");
		// String fileSeparator = File.separator;
		LOG.info("*************************************Temp folder :" + temp_folder);
		LOG.info("*************************************Temp folder :" + temp_folder + file.getFileName());
		File copied = new File(temp_folder, file.getFileName());
		file.write(copied);
		// LOG.info("File copied to temp folder");
		Workbook workbook = StreamingReader.builder().rowCacheSize(100)
				.bufferSize(4096) 
				.open(file.getStream()); 
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println(sheet.getSheetName());
		VerifyExcelFile verifyExcelFile = new VerifyExcelFile();
		return verifyExcelFile.getExcelFileverify(copied,tableName);


	}

}
