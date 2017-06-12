package com.leoni.forsimport.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.leoni.forsimport.model.Error;
import com.leoni.forsimport.model.User;
import com.leoni.forsimport.util.VerifyExcelFile;
import com.leoni.forsimport.services.ExcelStreamResponse;
import com.leoni.forsimport.services.TabNames;
import com.monitorjbl.xlsx.StreamingReader;

/**
 * Upload Excel file.
 */

public class Import extends BasePage {
	private static final Logger LOG = Logger.getLogger(Import.class);

	@Property
	@Persist
	@NotNull
	private String tableName;

	@Property
	private UploadedFile file;
	Error error = new Error();

	// Generally useful bits and pieces
	@Inject
	private TabNames tableNames;

	User user;

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

	/**
	 * @return
	 * 
	 */
	boolean test = false;

	public ExcelStreamResponse onSelectedFromUpload() {

		/**
		 * TODO
		 * test if tableName not exist in the data base
		 * add logOut ...
		 * add modification for page with profile  
		 */
		LOG.info("Starting");
		String temp_folder = System.getProperty("java.io.tmpdir");
		File copied = new File(temp_folder, file.getFileName());
		file.write(copied);
		Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(file.getStream());
		Sheet sheet = workbook.getSheetAt(0);
		VerifyExcelFile verifyExcelFile = new VerifyExcelFile();
		test = verifyExcelFile.isOkExcelStyle(sheet);
		return verifyExcelFile.getExcelFileverify(copied, tableName);
	}

	public boolean isOkExcel() {
		return test;
	}

}
