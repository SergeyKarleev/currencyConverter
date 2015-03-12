package ru.sergeykarleev.useuconverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

/**Класс выполняет запрос к сайту в асинхронном режиме и возвращает тело XML ответа
 * @author SergeyKarleev
 *
 */
public class MyXMLAsyncLoader extends AsyncTaskLoader<String> {

	final static String LOG_TAG = "myLogs";
	
	String mRequest = null;
		
	public MyXMLAsyncLoader(Context context, Bundle args) {
		super(context);				
	}

	@Override
	public String loadInBackground() {
		URL url;
		InputStream inputStream;
		
		try {
			url = new URL(mRequest);
			inputStream = url.openStream();
			return new Scanner(inputStream).useDelimiter("\\A").next();
		}catch (java.util.NoSuchElementException e) {
			return e.toString();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			return e1.toString();
		} 
		
	}
	


}
