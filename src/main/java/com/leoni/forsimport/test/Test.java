package com.leoni.forsimport.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {

	private CellStyle errorCellStyle;

	private CellStyle okCellStyle;
	private Comment comment;
	private Drawing drawing;
	private ClientAnchor anchor;
	private CreationHelper factory;
	public static void main(String[] args) {
		String dateString = /*cell.getDateCellValue().toString()*/"2016/02/03";
		String[] tab = dateString.split("/");
		System.out.println(String.format("%s%s%s", tab[0], tab[1], tab[2]));
		Test test = new Test();
		test.run();
		System.out.println("Done");
	}

	private void date() {
		
	}
	private void run() {

		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook("d:/test.xlsx");
			initCellStyles(workbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheetAt(0);
		initCellComment(workbook,sheet);
		int i = 0;
		for (Row r : sheet) {
			for (Cell cell : r) {
				markErroneousCell(cell, "", i,r);
			}
//				CreationHelper factory = workbook.getCreationHelper();
//				Drawing drawing = sheet.createDrawingPatriarch();
//				ClientAnchor anchor = factory.createClientAnchor();
//				anchor.setCol1(r.getCell(0).getColumnIndex());
//				anchor.setCol2(r.getCell(0).getColumnIndex() + 1);
//				anchor.setRow1(r.getRowNum());
//				anchor.setRow2(r.getRowNum() + 3);
//				comment = drawing.createCellComment(anchor);
//			    RichTextString str = factory.createRichTextString("Hello, World!");
//			    comment.setString(str);
//			    r.getCell(0).setCellComment(comment);
			i++;
		}
		try {
			File filetemp = new File("d:/" + System.currentTimeMillis() + ".xlsx");
			FileOutputStream excelFileOut = new FileOutputStream(filetemp);
			workbook.write(excelFileOut);
			excelFileOut.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Unknown error");
		}
	}
	private void initCellComment(Workbook wb, Sheet sheet){
		factory = wb.getCreationHelper();
		drawing = sheet.createDrawingPatriarch();
		anchor = factory.createClientAnchor();
	}

	private void initCellStyles(Workbook wb) {
		errorCellStyle = wb.createCellStyle();
		errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		errorCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		okCellStyle = wb.createCellStyle();
		okCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		okCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	}
	
	
	private void markErroneousCell(Cell cell, String message, int i,Row r) {
		if (i % 2 == 0) {
			cell.setCellStyle(okCellStyle);
		} else {
			cell.setCellStyle(errorCellStyle);
			anchor.setCol1(cell.getColumnIndex());
			anchor.setCol2(cell.getColumnIndex() + 1);
			anchor.setRow1(r.getRowNum());
			anchor.setRow2(r.getRowNum() + 3);
			Comment comment = drawing.createCellComment(anchor);
		    RichTextString str = factory.createRichTextString(message);
		    comment.setString(str);
		    comment.setAuthor("Apache POI");
		    cell.setCellComment(comment);
		}
	}

}
