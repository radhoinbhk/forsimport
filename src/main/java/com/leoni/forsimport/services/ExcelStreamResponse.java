package com.leoni.forsimport.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 * Class used to write response output stream.
 * @author Client
 *
 */
public class ExcelStreamResponse implements StreamResponse {
	
	private static final Logger LOG = Logger.getLogger(ExcelStreamResponse.class);
	
	private InputStream is;
	private String filename = "default";

	public ExcelStreamResponse(InputStream is, String fileName) {
		LOG.info("Starting");
		this.is = is;
		this.filename = fileName;
	}

	@Override
	public String getContentType() {
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}

	@Override
	public InputStream getStream() throws IOException {
		return is;
	}

	@Override
	public void prepareResponse(Response arg0) {
		arg0.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
	}
}
