package ru.sergeykarleev.useuconverter.classes;

import java.util.ArrayList;

import android.util.Log;

public class MyMonthQuotesObject {
	final static String LOG_TAG = "myLogs";
	
	private ArrayList<Double> quotesList;
	private static String month = "null";
	private static String year = "null";
	private static String valute = "null";
	
	private static MyMonthQuotesObject instance = null;
		
	
	private MyMonthQuotesObject() {
		super();		
		quotesList = new ArrayList<Double>();
	}


	public static MyMonthQuotesObject getInstance(){
		if (instance==null)
			instance = new MyMonthQuotesObject();
		return instance;
		
	}
	
	public Double[] getQuotesList() {		
		return quotesList.toArray(new Double[quotesList.size()]);
	}
	
	public void setQuotesList(Double[] doubles) {
		for (int i=0;i<doubles.length;i++) {
			quotesList.add(doubles[i]);
		}
	}
	
	public void clearQuotesList(){
		this.quotesList.clear();
	}
	
	public boolean isEmptyQuotes(){
		try{
			for (int i=0; i<quotesList.size();i++)
				if (quotesList.get(i)!=0.0)
					return false;	
		}catch(Exception e){
			e.printStackTrace();
		}		
		return true;
	}
	
	public boolean isComparised(String year, String month, String valute){
		if (
				this.year.equals(year) &
				this.month.equals(month) &
				this.valute.equals(valute))
			return true;
		return false;		
	}
	
	public void setData(String year, String month, String valute){
		this.year = year;
		this.month = month;
		this.valute = valute;
	}

	public String getMonthAndYear(){
		return "Котировки на "+month+" "+year;
	}

	public String[] getQuoteListForTable(){
		String[] qList = new String[quotesList.size()];
		for (int i=0;i<quotesList.size();i++){
			qList[i] = "День "+(i+1)+": "+quotesList.get(i)+" rub";
		}
		
		Log.d(LOG_TAG, "Таблица будет состоять из:");
		for (String string : qList) {
			Log.d(LOG_TAG,string);
		}
		
		return qList;	
	}
	
	
}
