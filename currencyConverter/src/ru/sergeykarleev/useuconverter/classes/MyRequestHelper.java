package ru.sergeykarleev.useuconverter.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

import ru.sergeykarleev.useuconverter.R;

/**
 * @author skar011
 * 
 */
public abstract class MyRequestHelper {

	private final static String LOG_TAG = "myLogs";
	private final static String DAY_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
	private final static String MONTH_URL = "http://www.cbr.ru/scripts/XML_dynamic.asp?";
	// "http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=02/03/2001&date_req2=14/03/2001&VAL_NM_RQ=R01235"

	final static String VALUTE_USD_ID = "R01235";
	final static String VALUTE_EUR_ID = "R01239";

	/**
	 * Формирование запроса на дневные котировки по всем валютам
	 */
	public static String getDayRequest(int year, int monthOfYear, int dayOfMonth) {		
		return DAY_URL + getDate(year, monthOfYear, dayOfMonth);
	}

	/**
	 * Формирование запроса на месячные котировки по определенной валюте
	 */
	public static String getMonthRequest(int year, int monthOfYear,
			String valute) {
		String dateStart = getDate(year, monthOfYear, 1);
		String dateEnd = getDateEnd(year, monthOfYear);
		String valuteID = null;
		
		switch (valute) {
		case "USD":
			valuteID = VALUTE_USD_ID;
			break;
		case "EUR":
			valuteID = VALUTE_EUR_ID;
			break;
		default:
			valuteID = VALUTE_USD_ID;
			break;
		}

		return MONTH_URL + "date_req1=" + dateStart + "&date_req2=" + dateEnd
				+ "&VAL_NM_RQ=" + valuteID;
	}

	private static String getDateEnd(int year, int monthOfYear) {
		int lastDay;

		GregorianCalendar c = new GregorianCalendar();

		switch (monthOfYear) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			lastDay = 31;
			break;
		case 1:
			if (c.isLeapYear(year))
				lastDay = 29;
			else
				lastDay = 28;
			break;

		default:
			lastDay = 30;
			break;
		}
		
		return getDate(year, monthOfYear, lastDay);
	}

	/**
	 * Метод возвращает дату в правильном формате
	 */
	public static String getDate(int year, int monthOfYear, int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(year, monthOfYear, dayOfMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data = sdf.format(c.getTime());
		return data;
	}
}
