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
public class MyDAYParser implements XMLParser{

	final static String LOG_TAG = "myLogs";

	final static String TAG_NAME = "Valute";
	final static String TAG_ATTR_NAME = "ID";

	final static String TAG_USD_ID = "R01235";
	final static String TAG_EUR_ID = "R01239";

	final static String TAG_VALUE = "Value";

	double USD = 0.0;
	double EUR = 0.0;

	public MyDAYParser(String xmlFile) {
		super();

		// TODO: реализовать проверку строки на техническую с ошибками или
		// рабочую с данными
		try {
			Log.d(LOG_TAG, "Стартуем парсер");
			startParsing(xmlFile);
		} catch (Exception e) {
			Log.d(LOG_TAG, e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void startParsing(String xml) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xml));
		
		Boolean currentValuteUSD = false;
		Boolean currentValuteEUR = false;
		Boolean currentValue = false;

		while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				if (parser.getAttributeCount() > 0) {
					switch (parser.getAttributeValue(0)) {
					case TAG_USD_ID:
						currentValuteUSD = true;
						break;
					case TAG_EUR_ID:
						currentValuteEUR = true;

						break;
					default:
						break;
					}
				}

				if (parser.getName().equals(TAG_VALUE)) {
					currentValue = true;
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals(TAG_NAME)) {
					currentValuteUSD = false;
					currentValuteEUR = false;
				}
				if (parser.getName().equals(TAG_VALUE) && currentValue) {
					currentValue = false;
				}
				break;
			case XmlPullParser.TEXT:
				if (currentValuteUSD && currentValue)
					USD = Double.parseDouble(parser.getText().replaceAll(",","."));			
					
				if (currentValuteEUR && currentValue)
					EUR = Double.parseDouble(parser.getText().replaceAll(",","."));
					
				break;
			default:
				break;
			}

			parser.next();
		}		
	}

	@Override
	public double getValute(int valute) {
		switch (valute) {
		case VALUTE_USD:
			return USD;
			
		case VALUTE_EUR:
			return EUR;
			
		default:
			break;
		}
		return 0;
	}
}
