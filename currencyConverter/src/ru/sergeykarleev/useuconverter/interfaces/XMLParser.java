package ru.sergeykarleev.useuconverter.interfaces;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public interface XMLParser {
	final static String LOG_TAG = "myLogs";

	final static String TAG_NAME = "Valute";
	final static String TAG_ATTR_NAME = "ID";

	final static String TAG_USD_ID = "R01235";
	final static String TAG_EUR_ID = "R01239";

	final static String TAG_VALUE = "Value";

	void startParsing(String xml) throws XmlPullParserException, IOException;

	public double getValute(int valute);
}
