package ru.sergeykarleev.useuconverter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
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

	public String getDate(){
		if (mode == MODE_DDMMYY)
			return "date: "+date;
		else
			return "dateStart: "+dateStart+" dateEnd: "+dateEnd;
		
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
	
	
	
//	 public class NewThread extends AsyncTask<String, Void, String> 
//	    {
//	        //public List<StringWrapper> wrapp = new ArrayList<StringWrapper>();
//	        @Override
//	        protected String doInBackground(String... params) 
//	        {
//	            
//	            try 
//	            {
//	                URL url = new URL("http://katolik.ru/mir.feed?type=rss");
//	                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
//	                {
//	                    InputStream stream = conn.getInputStream();
//	                   // wrapp.add(new StringWrapper("dsdsdsd", conn.toString()));
//	                    
//	                }               
//	 
//	            } 
//	            catch (Exception e) 
//	            {
//	          //      wrapp.add(new StringWrapper("Жопа", "Большая Жопа"));
//	                e.printStackTrace();
//	            }
//	            return null;
//	        }
//	        @Override
//	        protected void onPostExecute(String result) 
//	        {
//	                           
//	        }       
//	        
//	    }   
}
