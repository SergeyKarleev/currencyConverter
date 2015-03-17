package ru.sergeykarleev.useuconverter.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class MyRequestHelper {

	private final static String DAY_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req="; 
	
	public MyRequestHelper() {
		super();
	
	}

	/**Метод 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static String getDayRequest(int year, int monthOfYear,
			int dayOfMonth){
		
		Calendar c = Calendar.getInstance();
		c.set(year, monthOfYear, dayOfMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(c.getTime());		
		
		
	}
}
