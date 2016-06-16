package com.sonat.blog.util.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatValidator {

	public static Date Parse(String datetime,String dateTimeFormat){
			DateFormat formatter = new SimpleDateFormat(DateTimeConstants.DATETIME_FORMAT);
			Date date=new Date();
			
			try {
				date	= formatter.parse(datetime);
			} catch (ParseException e) {
			  System.out.println(e.getMessage());
				e.printStackTrace();
			}catch (ClassCastException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
		return date;
	}
}
