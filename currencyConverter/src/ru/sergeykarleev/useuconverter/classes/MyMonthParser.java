package ru.sergeykarleev.useuconverter.classes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.graphics.Path.FillType;
import android.util.Log;

import ru.sergeykarleev.useuconverter.interfaces.XMLParser;

public class MyMonthParser implements XMLParser {

	private final static String TAG_VALUE = "Value";
	private final static String TAG_VALCURSE = "ValCurs";

	private static int lastDay;
	private static Double[] quotes = new Double[31];

	// private ArrayList<String> techArray;
	// private ArrayList<Double> quotes;

	public MyMonthParser(String xmlFile) {
		super();
		try {
			// techArray = new ArrayList<String>();
			this.lastDay = getLastDay(xmlFile);
			startParsing(xmlFile);

		} catch (XmlPullParserException e) {
			Log.d(LOG_TAG, e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(LOG_TAG, e.toString());
			e.printStackTrace();
		}
	}

	private int getLastDay(String xml) {		
		
		int startIndex = xml.lastIndexOf("DateRange2=")
				+ String.valueOf("DateRange2='").length();
		int endIndex = startIndex + 2;
		
		try {			
			return Integer.parseInt(xml.substring(startIndex, endIndex));
		} catch (Exception e) {
			Log.d(LOG_TAG, "Ошибка преобразования формата " + e);
			return 28;
		}
	}

	@Override
	public void startParsing(String xml) throws XmlPullParserException,
			IOException {

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xml));

		String tagName = null;
		int i = 0;
		Arrays.fill(quotes, 0.0);

		// TODO: Парсим документ
		while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				tagName = parser.getName();
				if (parser.getAttributeCount() > 0
						&& !parser.getName().equals(TAG_VALCURSE)) {
					i = Integer.valueOf(parser.getAttributeValue(0).substring(
							0, 2)) - 1;
					Log.d(LOG_TAG, "i = " + i);
					// techArray.add(parser.getAttributeValue(0));
				}
				break;
			case XmlPullParser.TEXT:
				if (tagName.equals(TAG_VALUE)) {
					Log.d(LOG_TAG,
							"Записываем в день " + (i+1) + " значение "
									+ parser.getText());
					try {
						quotes[i] = Double.parseDouble(parser.getText()
								.replace(",", "."));
					} catch (Exception e) {
						e.printStackTrace();
						Log.d(LOG_TAG, e.toString());
					}
					// techArray.add(parser.getText());
					tagName = "null";
				}

				break;
			default:
				break;
			}
			parser.next();

		}
		
		fillindQuotes(quotes);
		// parseQuotes(techArray);
	}

	/**
	 * Дополняем пустые ячейки массива котировок предыдущим ненулевым значением
	 * 
	 * @param quotes
	 */
	private void fillindQuotes(Double[] quotes) {
		for (int i = 0; i < lastDay; i++) {
			if (i > 0 && quotes[i]==0.0) {
				quotes[i] = quotes[i - 1];
				int j = i + 1;
				Log.d(LOG_TAG, "День " + j + " значение: " + quotes[i]);
			}else{
				Log.d(LOG_TAG, "День "+(i+1)+" значение: "+quotes[i]);
			}
		
			
		}
		Log.d(LOG_TAG, "Массив успешно заполнен");
	}

	public Double[] getQuotesList() {
		Double[] qt = new Double[lastDay];
		//Arrays.fill(qt, 0.0);
		for (int i = 0; i < lastDay; i++) {
			qt[i] = quotes[i];
		}
		return qt;
	}

	@Override
	public double getValute(int valute) {
		// TODO Auto-generated method stub
		return 0;
	}
}
