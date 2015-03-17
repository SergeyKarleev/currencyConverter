package ru.sergeykarleev.useuconverter.classes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import ru.sergeykarleev.useuconverter.interfaces.XMLParser;

public class MyMonthParser implements XMLParser {
		
	Double[] USD = null;
	Double[] EUR = null;

	private final static String DATE = "Date";
	private final static String VALUE = "Value";
	
	ArrayList<Double> quotes = new ArrayList<>();
	ArrayList<HashMap<String, String>> dates = new ArrayList<HashMap<String,String>>();
	
	public MyMonthParser(String xmlFile) {
		super();
		try {
			startParsing(xmlFile);
		} catch (XmlPullParserException e) {
			Log.d(LOG_TAG, e.toString());
			e.printStackTrace();
		} catch (IOException e){
			Log.d(LOG_TAG, e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void startParsing(String xml) throws XmlPullParserException,
			IOException {
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xml));
		
		HashMap<String, String> map = new HashMap<>();
		
		//TODO: Парсим документ
		while(parser.getEventType()!= XmlPullParser.END_DOCUMENT){
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				if (parser.getAttributeCount()>0){		
					map.put(DATE, parser.getAttributeValue(0));
				}
					
					
				break;

			default:
				break;
			}
			
			parser.next();
			
		}
	}

	@Override
	public double getValute(int valute) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<Double> getQuoteList() {
		return quotes;
	}

}
