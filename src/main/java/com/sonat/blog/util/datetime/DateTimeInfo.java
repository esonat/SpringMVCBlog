package com.sonat.blog.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeInfo {
	public static String getDateToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today;
		try{
			today=sdf.parse(new Date().toString());
		}catch(ParseException e){
			return null;
		}
		return today.toString();
	}
	
	public static String getDateTimeNow(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		Date today;
		try{
			today=sdf.parse(calendar.getTime().toString());
		}catch(ParseException e){
			return null;
		}
		return today.toString();
	}
	
	public static String getDateTimeToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
	
		Date today;
		try{
			today=sdf.parse(calendar.getTime().toString());
		
		}catch(ParseException e){
			return null;
		}
		return today.toString();
	}
	
	public static String getDateTimeThisWeek(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 calendar.set(Calendar.DAY_OF_WEEK, 1);
		 
		 Date thisweek;
		 try{
			 thisweek=sdf.parse(calendar.getTime().toString());
		 }catch(ParseException e){
			 return null;
		 }
		 return thisweek.toString();
	}
	
	public static String getDateTimeThisMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 calendar.set(Calendar.DAY_OF_MONTH, 1);
		 
		 Date thisMonth;
		 try{
			 thisMonth=sdf.parse(calendar.getTime().toString());
		 }catch(ParseException e){
			 return null;
		 }
		 return thisMonth.toString();
	}
	
	public static String getDateTimeThisYear(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 calendar.set(Calendar.DAY_OF_YEAR, 1);
		 
		 Date thisMonth;
		 try{
			 thisMonth=sdf.parse(calendar.getTime().toString());
		 }catch(ParseException e){
			 return null;
		 }
		 return thisMonth.toString();
	}	
}
