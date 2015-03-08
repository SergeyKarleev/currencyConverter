package ru.sergeykarleev.useuconverter;

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
	}

}
