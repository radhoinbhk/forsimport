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
		table = export.getStructurTable();
		About.setExcel(table);

	}
}
