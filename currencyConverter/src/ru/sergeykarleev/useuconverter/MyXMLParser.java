package ru.sergeykarleev.useuconverter;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

/**
 * Класс, умеющий добывать различную информацию из загруженного XML файлика
 * 
 * @author SergeyKarleev
 * 
 */
public class MyXMLParser {

	final static String LOG_TAG = "myLogs";

	final static String TAG_NAME = "Valute";
	final static String TAG_ATTR_NAME = "ID";

	final static String TAG_USD_ID = "R01235";
	final static String TAG_EUR_ID = "R01239";

	final static String TAG_VALUE = "Value";

	double USD = 0.0;
	double EUR = 0.0;

	public MyXMLParser(String xmlFile) {
		super();

		// TODO: реализовать проверку строки на техническую с ошибками или
		// рабочую с данными
		try {
			Log.d(LOG_TAG, "Стартуем парсер");
			startParser(xmlFile);
		} catch (Exception e) {
			Log.d(LOG_TAG, e.toString());
			e.printStackTrace();
		}

	}

	public double getUSD() {
		return USD;
	}

	public double getEUR() {
		return EUR;
	}

	private void startParser(String xml) throws XmlPullParserException,
			IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xml));

		StringBuilder sb = new StringBuilder();

		while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {			
			
			try {				
				if (parser.getAttributeValue(0).equals(TAG_EUR_ID))
				{
					String tag = parser.getName();
					sb.append("EURO: ");
					
					while (parser.getEventType()!=XmlPullParser.END_TAG && parser.getName().equals(tag))
					{
						try{
							Log.d(LOG_TAG, parser.getName());
						}catch(Exception e){}
						if (parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals("Value"))
							sb.append("yes");
						parser.next();
					}					
				}
								
				
			} catch (Exception e) {

			}

			// if (parser.getEventType()==XmlPullParser.START_TAG &&
			// parser.getName()==TAG_NAME &&
			// parser.getAttributeValue(0)==TAG_EUR_ID){
			// while (parser.getName()!=TAG_VALUE ||
			// parser.getEventType()!=XmlPullParser.END_DOCUMENT){
			// try {
			// parser.nextTag();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if (parser.getName()==TAG_VALUE){
			// parser.nextText();
			// USD = Double.parseDouble(parser.getText().toString());
			// }
			// }
			//
			// if (parser.getEventType()==XmlPullParser.START_TAG &&
			// parser.getName()==TAG_NAME &&
			// parser.getAttributeValue(0)==TAG_USD_ID){
			// while (parser.getName()!=TAG_VALUE ||
			// parser.getEventType()!=XmlPullParser.END_DOCUMENT){
			// try {
			// parser.nextTag();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if (parser.getName()==TAG_VALUE){
			// parser.nextText();
			// EUR = Double.parseDouble(parser.getText().toString());
			// }
			// }
			parser.next();
		}
		Log.d(LOG_TAG, "StringBuilder: " + sb.toString());
		Log.d(LOG_TAG, "Парсинг выполнен.\nUSD: " + USD + "\nEUR: " + EUR);
	}
}
