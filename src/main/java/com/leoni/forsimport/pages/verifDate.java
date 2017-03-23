package com.leoni.forsimport.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class verifDate {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");

	public static void main(String[] args) throws ParseException {
		String dateString = "2016/02/03";
		
		String[] tab = dateString.split("/");
		System.out.println(String.format("tab %s, %s, %s", tab[0], tab[1], tab[2]));
//		Date date = SDF.parse(dateString);
//		if (date != null) {
//			Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		System.out.println("Year :" + calendar.get(Calendar.YEAR));
//		System.out.println("Month :" + (calendar.get(Calendar.MONTH) + 1));
//		System.out.println("day   :" + calendar.get(Calendar.DAY_OF_MONTH));
//		}
	}
}
