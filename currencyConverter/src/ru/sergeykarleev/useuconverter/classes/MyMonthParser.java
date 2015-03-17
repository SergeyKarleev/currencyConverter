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
	private final static String TAG_VALUE = "Value";
	private final static String TAG_VALCURSE = "ValCurs";
	
	ArrayList<Double> quotes;
	
	private ArrayList<String> technicalArray;	
	
	public MyMonthParser(String xmlFile) {
		super();
		try {
			technicalArray = new ArrayList<String>();
			quotes = new ArrayList<Double>();			
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
				
		String tagName = null;
		
		//TODO: Парсим документ
		while(parser.getEventType()!= XmlPullParser.END_DOCUMENT){
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:				
				tagName = parser.getName();
				if (parser.getAttributeCount()>0 && !parser.getName().equals(TAG_VALCURSE)){		
					technicalArray.add(parser.getAttributeValue(0));
				}					
				break;			
			case XmlPullParser.TEXT:
				if (tagName.equals(TAG_VALUE)){
					technicalArray.add(parser.getText());
					tagName = "null";
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
