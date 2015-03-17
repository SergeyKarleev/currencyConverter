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
	
	private ArrayList<String> techArray;
	private ArrayList<Double> quotes;
	
	public MyMonthParser(String xmlFile) {
		super();
		try {
			techArray = new ArrayList<String>();						
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
		
		//TODO: ������ ��������
		while(parser.getEventType()!= XmlPullParser.END_DOCUMENT){
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:				
				tagName = parser.getName();
				if (parser.getAttributeCount()>0 && !parser.getName().equals(TAG_VALCURSE)){		
					techArray.add(parser.getAttributeValue(0));
				}					
				break;			
			case XmlPullParser.TEXT:
				if (tagName.equals(TAG_VALUE)){
					techArray.add(parser.getText());
					tagName = "null";
				}
					
				break;
			default:
				break;
			}			
			parser.next();
			
		}		
		parseQuotes(techArray);
	}	
	
	@Override
	public double getValute(int valute) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void parseQuotes(ArrayList<String> techArray){
		
		//TODO: ����� �� ������ ��������� ��� ��������� �������� ��������� � ������ quote, �������� ����������� ���������� ���� ������ �������� �� �������
		/*
		 * 1. ���������� ���������� ���� � ��������� ������ lastDay
		 * 2. ������� ������ Double[] days �������� �� �.1
		 * 3. �� ������ ������� ������� days �������������, ���� �� ����� ���� � ������� techArray. ���� ��, �� ���������� ��������� �������� �� techArray (��� ����� ���������)
		 * ���� ���, �� ���������� �������� �� ������� days.
		 */
		quotes = new ArrayList<Double>();
		int arrSize = techArray.size();
		String lastData = techArray.get(arrSize-2);
		String lastDayStr = lastData.substring(0, 2);
		int lastDay = Integer.valueOf(lastDayStr);
		
		Log.d(LOG_TAG, "TechArray: "+"\n�������� �������� = "+arrSize/2+
				"\n���������� ���� = "+lastDay);
		for (int i = 0; i<techArray.size();i++) {
			if (i%2!=0)
				Log.d(LOG_TAG, techArray.get(i));
		}
		
		
		
	}
	
	public ArrayList<String> getQuoteList() {
		return techArray;
	}

}
