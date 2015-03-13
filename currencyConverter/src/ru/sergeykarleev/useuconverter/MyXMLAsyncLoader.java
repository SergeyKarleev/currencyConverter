package ru.sergeykarleev.useuconverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


/**Класс выполняет запрос к сайту в асинхронном режиме и возвращает тело XML ответа
 * @author SergeyKarleev
 *
 */
public class MyXMLAsyncLoader extends AsyncTaskLoader<String> {

	final static String LOG_TAG = "myLogs";
	
	String mRequest = null;
		
	public MyXMLAsyncLoader(Context context, Bundle args) {
		super(context);
		mRequest = args.getString("REQUEST");
		Log.d(LOG_TAG, "Конструктор AsyncLoader загрузил запрос: "+mRequest);
	}

	@Override
	public String loadInBackground() {
		URL url;
		InputStream inputStream;
		
		try {
			url = new URL(mRequest);
			inputStream = url.openStream();
			Log.d(LOG_TAG, "Возвращен текст");
			return new Scanner(inputStream).useDelimiter("\\A").next();
		}catch (java.util.NoSuchElementException e) {
			Log.d(LOG_TAG, e.toString());
			return e.toString();
		}catch (IOException e1) {
			Log.d(LOG_TAG, e1.toString());
			return e1.toString();
		} 
		
	}
	


}
