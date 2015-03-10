package ru.sergeykarleev.useuconverter;

import android.content.Context;
import android.widget.Toast;

/**
 * Класс отвечает за соединение с сервером и получение котировок с последующим
 * формированием списка
 * 
 * @author SergeyKarleev
 * 
 */
/**
 * @author sergey
 * 
 */
/**
 * @author skar011
 *
 */
public class MyCurrencyClass {


	final static int MODE_DDMMYY = 1;
	final static int MODE_MMYY = 2;

	// final static String URL_DAY =
	// "www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002"
	final static String URL_DAY = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";

	// final static String URL_MONTH =
	// "http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=02/03/2001&date_req2=14/03/2001&VAL_NM_RQ=R01235";

	int mode;

	int day;
	int month;
	int year;
	
	double multiplicator_usd_rur = 1;
	double multiplicator_eur_rur = 1;
	
	String date;
	String dateStart;
	String dateEnd;

	/**
	 * Конструктор для определения котировок дня
	 * 
	 * @param day
	 * @param month
	 * @param year
	 */
	public MyCurrencyClass(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		mode = MODE_DDMMYY;
		date = mConvertToDate(day, month, year);		
	}

	/**
	 * Конструктор для определения котировок месяца
	 * 
	 * @param month
	 * @param year
	 */
	public MyCurrencyClass(int month, int year) {
		this.month = month;
		this.year = year;
		mode = MODE_MMYY;
		dateStart = mConvertToDate(1, month, year);
		dateEnd = getEndDate(month, year);		  
	}

	
	
	/**Определение количества дней в месяце
	 */
	private String getEndDate(int month, int year){
		
		if(month==1 || month == 3 || month==5 || month==7 || month==8 || month == 10 || month == 12)
		{
			return mConvertToDate(31, month, year);
		}
		else if (month == 4 || month == 6 || month ==9 || month == 11) {
			return mConvertToDate(30, month, year);
		}
		else if (year%4==0 && ((year%100!=0)||(year%400==0))) {
			return mConvertToDate(29, month, year);
		}
		else return mConvertToDate(28, month, year);
		
	}

	
	/**Метод превращает день, месяц и год в строку нужного вида
	 */
	private String mConvertToDate(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		if (day < 10)
			sb.append("0" + day + "/");
		else
			sb.append(day + "/");

		if (month < 10)
			sb.append("0" + month + "/");
		else
			sb.append(month + "/");

		sb.append(year);
		return sb.toString();
	}
	
	
	/**Класс, умеющий извлекать различную информацию из полученного от ЦБ файла XML
	 * @author SergeyKarleev
	 *
	 */
	public class MyXMLParser {
		
	}

}
