package ru.sergeykarleev.useuconverter;

public class MyMonthCurrency {
private static MyMonthCurrency instance = null;
private static int year;
private static int month;
private static Double[] prices;

	private MyMonthCurrency(){
		
	}
	
	public static MyMonthCurrency getInstance(){
		if (instance == null){
			instance = new MyMonthCurrency();
		}
		return instance;
	}
	
	public Double[] getPrices(int year, int month){
		if (year==this.year && month == this.month)
			return prices;
		
		
		return null;		
	}

}
