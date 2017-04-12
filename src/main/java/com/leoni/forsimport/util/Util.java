/**
 * 
 */
package com.leoni.forsimport.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.leoni.forsimport.pages.Import;

/**
 * @author HP This class contains static methods which are used to perform data
 *         checks.
 */
public class Util {

	private static final String DATE_PATTERN = "\\d{2}/\\d{2}/\\d{4}";
	private static final Logger LOG = Logger.getLogger(Import.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "02-mars-2017";
		System.out.println(s.matches(DATE_PATTERN));
		String[] tab = s.split("-");
		System.out.println(String.format("%s%s%s", tab[2], tab[1], tab[0]));
	}

	/**
	 * checks whether the given cell contains date of type date.
	 * 
	 * @param cell
	 * @param workbook
	 * @return
	 */
	public static boolean isDate(Cell cell, Workbook workbook) {
		return HSSFDateUtil.isCellDateFormatted(cell);
		// CellStyle cellStyle = workbook.createCellStyle();
		// CreationHelper createHelper = workbook.getCreationHelper();
		// cellStyle.setDataFormat(
		// createHelper.createDataFormat().getFormat("d/m/yy"));
		// LOG.info(HSSFDateUtil.isCellDateFormatted(cell));
		// LOG.info("util.java cell.getCellStyle"+cell.getCellStyle());
		// return cell.getCellStyle() == cellStyle;
	}

	private static void main() {
		System.out.println("Test");
	}

}
