package forsimport;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestCellFormatting {

	@Test
	public void testDateCell() {
		try {
			FileInputStream inputStream = new FileInputStream("exemple.xlsx");
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rows-1 ; i++) {
				Row r = sheet.getRow(i);
				for (int j = 0; j < r.getLastCellNum(); j++) {
					Cell cell = r.getCell(j);
					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							System.out.println(i+")cell numeric: "+cell);
							 Date date = cell.getDateCellValue();
							 CellStyle cellStyle = workbook.createCellStyle();
							 CreationHelper createHelper = workbook.getCreationHelper();
							 cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyyMMdd"));
//							 cell.setCellValue("10-05-2017");
							 cell.setCellStyle(cellStyle);
//							 assertEquals("", "10-05-2017", cell.getStringCellValue());
							 System.out.println(i+")cell date: "+cell);
							// binds the style you need to the cell.
						}
					}
				}
			}
//			 FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
//			    workbook.write(fileOut);
//			    fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
