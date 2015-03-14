package ru.sergeykarleev.useuconverter;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public interface XMLParser {
	final static int VALUTE_USD = 1;
	final static int VALUTE_EUR = 2;
	
	void startParsing(String xml) throws XmlPullParserException, IOException;
	public double getValute(int valute);
	
}
