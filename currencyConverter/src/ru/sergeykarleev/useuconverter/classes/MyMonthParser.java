package ru.sergeykarleev.useuconverter.classes;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import ru.sergeykarleev.useuconverter.interfaces.XMLParser;

public class MyMonthParser implements XMLParser {
		
	Double[] USD = null;
	Double[] EUR = null;

	
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
	}

	@Override
	public double getValute(int valute) {
		// TODO Auto-generated method stub
		return 0;
	}

}
