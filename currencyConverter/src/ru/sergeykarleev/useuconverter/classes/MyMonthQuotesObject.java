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
}
