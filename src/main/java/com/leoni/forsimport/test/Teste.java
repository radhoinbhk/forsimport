package com.leoni.forsimport.test;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.*;

public class Teste {
	public static void main(String[] args) {
		XSSFWorkbook my_xlsx_workbook = null;
		try {
			FileInputStream input_document = new FileInputStream(new File("D:\test.xlsx"));
			my_xlsx_workbook = new XSSFWorkbook(input_document); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		XSSFSheet my_sheet = my_xlsx_workbook.getSheetAt(0); 
		CTAutoFilter sheetFilter=my_sheet.getCTWorksheet().getAutoFilter();
		// /* Step -2: Create Filter Column Object and Define Column ID */
		// CTFilterColumn myFilterColumn=sheetFilter.insertNewFilterColumn(0);
		// myFilterColumn.setColId(1L);

		// /* Step-3: Create a Color Filter and set ID */
		// CTColorFilter colorFilter=myFilterColumn.addNewColorFilter();
		// colorFilter.setDxfId(0L);       
		/* Step-4: Update StyleSheet and Attach Filter Color Details */         
		CTStylesheet wbStyle=my_xlsx_workbook.getStylesSource().getCTStylesheet();
		CTDxfs dxSet=wbStyle.addNewDxfs();
		dxSet.setCount(1L);
		CTPatternFill myFill=dxSet.addNewDxf().addNewFill().addNewPatternFill();
		myFill.setPatternType(STPatternType.SOLID);
		CTColor fgColor=myFill.addNewFgColor();
		CTColor bgColor=myFill.addNewBgColor();         
		byte[] fg=javax.xml.bind.DatatypeConverter.parseHexBinary("FF008000");  // Foreground filter color = green      
		byte[] bg=javax.xml.bind.DatatypeConverter.parseHexBinary("FFFFFFFF"); // background filter color="white"
		fgColor.setRgb(fg);
		bgColor.setRgb(bg);
		/* Step-5: Refresh Records to Match Filter Condition */
		XSSFRow r1;             
		/* Step-6: Loop through Rows and Apply Filter */
		for(Row r : my_sheet) {
		        for (Cell c : r) {
		                //if foreground filter color is not green then hide the record
		                if ( c.getColumnIndex()==1  && c.getCellStyle().getFillForegroundColor() !=17){
		                        r1=(XSSFRow) c.getRow();
		                        if (r1.getRowNum()!=0) { /* Ignore top row */
		                        /* Hide Row that does not meet Filter Criteria */
		                                r1.getCTRow().setHidden(true); }
		                }                               
		        }
		}               
		/* Step-7: Write changes to the workbook */
		try {
			FileOutputStream out = new FileOutputStream(new File("D:\test2.xlsx"));
			my_xlsx_workbook.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
