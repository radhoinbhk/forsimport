package com.leoni.forsimport.pages;

import java.io.File;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

/**
 * Upload Excel file
 */
public class Index {
	@Property
	private UploadedFile file;

	public void onSuccess() {
		File copied = new File("C:/ExcelFile/" + file.getFileName());

		file.write(copied);
	}

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String message;

	Object onUploadException(FileUploadException ex) {
		message = "Upload exception: " + ex.getMessage();

		return this;
	}

}