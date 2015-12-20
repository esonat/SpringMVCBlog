package com.sonat.blog.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator {

	public static String Parse(String datetime,String dateTimeFormat)
	throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		sdf.setLenient(false);
		
		String result=sdf.parse(datetime).toString();
		return result;
	}
}
