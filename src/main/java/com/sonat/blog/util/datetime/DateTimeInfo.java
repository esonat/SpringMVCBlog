package com.sonat.blog.util.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeInfo {
	public static Date getDateToday(){
		DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		Date date;
		try{
			String str=dateFormat.format(cal.getTime());
			date=dateFormat.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		
		return date;
	}
	
	public static Date getDateTimeNow(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.DATETIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		Date date;
		
		try{
			String str=dateFormat.format(cal.getTime());
			date=dateFormat.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		
		return date;
	}
	
	public static Date getDateTimeToday(){
		DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.DATETIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);	
		Date date;		
		try{
			String str=dateFormat.format(cal.getTime());
			date=dateFormat.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		
		return date;
	}
	
	public static Date getDateTimeThisWeek(){
		DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.DATETIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);

		Date date;
		
		try{
			String str=dateFormat.format(cal.getTime());
			date=dateFormat.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}	
		
		return date; 		 
	}
	
	public static Date getDateTimeThisMonth(){
		DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.DATETIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);

		Date date;
		try{
			String str=dateFormat.format(cal.getTime());
			date=dateFormat.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		
		return date;
	}
	
	public static Date getDateTimeThisYear(){
		DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.DATETIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);

		Date date;
		try{
			String str=dateFormat.format(cal.getTime());
			date=dateFormat.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		
		return date; 	
	}	
}
