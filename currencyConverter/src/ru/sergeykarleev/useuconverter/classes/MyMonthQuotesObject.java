package ru.sergeykarleev.useuconverter.classes;

import java.util.ArrayList;

public class MyMonthQuotesObject {
	private ArrayList<Double> quotesList;
	private String month = "null";
	private String year = "null";
	private String valute = "null";
	
	private static MyMonthQuotesObject instance = null;
		
	
	private MyMonthQuotesObject() {
		super();		
		quotesList  = new ArrayList<>();
	}


	public static MyMonthQuotesObject getInstance(){
		if (instance==null)
			instance = new MyMonthQuotesObject();
		return instance;
		
	}
	
	public ArrayList<Double> getQuotesList() {
		return quotesList;
	}
	
	public void setQuotesList(ArrayList<Double> quotesList) {
		this.quotesList = quotesList;
	}
	
	public void clearQuotesList(){
		this.quotesList.clear();
	}
	
	public boolean isEmptyQuotes(){		
		return quotesList.isEmpty();
		
	}
	
	public boolean isComparised(String year, String month, String valute){
		if (
				this.year.equals(year) &
				this.month.equals(month) &
				this.valute.equals(valute))
			return true;
		return false;
		
	}
}
