package ru.sergeykarleev.useuconverter.classes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


/** ласс выполн€ет запрос к сайту в асинхронном режиме и возвращает тело XML ответа
 * @author SergeyKarleev
 *
 */
public class MyXMLAsyncLoader extends AsyncTaskLoader<String> {

	final static String LOG_TAG = "myLogs";
	
	String mRequest = null;
	Context context;
		
	public MyXMLAsyncLoader(Context context, Bundle args) {
		super(context);
		this.context = context;
		mRequest = args.getString("REQUEST");
		//Log.d(LOG_TAG, " онструктор AsyncLoader загрузил запрос: "+mRequest);
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
			Toast.makeText(context, e1.toString(), Toast.LENGTH_SHORT).show();
			return e1.toString();
		} 
		
	}
	


}
