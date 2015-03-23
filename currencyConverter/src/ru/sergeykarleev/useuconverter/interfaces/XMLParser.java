package ru.sergeykarleev.useuconverter.interfaces;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import ru.sergeykarleev.useuconverter.R.string;

public interface XMLParser {
	final static String LOG_TAG = "myLogs";

	final static String TAG_NAME = "Valute";
	final static String TAG_ATTR_NAME = "ID";
	
	final static int VALUTE_USD = 1;
	final static int VALUTE_EUR = 2;

	final static String TAG_USD_ID = "R01235";
	final static String TAG_EUR_ID = "R01239";

	final static String TAG_VALUE = "Value";

	void startParsing(String xml) throws XmlPullParserException, IOException;
	public double getValute(int valute);
}
