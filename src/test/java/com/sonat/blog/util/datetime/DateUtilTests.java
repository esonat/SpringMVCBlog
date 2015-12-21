package com.sonat.blog.util.datetime;

import org.junit.Test;

public class DateUtilTests {
	
	@Test
	public void PrintDateToday(){
		System.out.println("Today date:"+DateTimeInfo.getDateToday());
	}
	@Test
	public void PrintDateTimeToday(){
		System.out.println("Today datetime:"+DateTimeInfo.getDateTimeToday());
	}
	@Test
	public void PrintDateTimeNow(){
		System.out.println("Now datetime:"+DateTimeInfo.getDateTimeNow());
	}
	@Test
	public void PrintDateTimeThisWeek(){
		System.out.println("This week datetime:"+DateTimeInfo.getDateTimeThisWeek());
	}
	@Test
	public void PrintDateTimeThisMonth(){
		System.out.println("This month datetime:"+DateTimeInfo.getDateTimeThisMonth());
	}
	@Test
	public void PrintDateTimeThisYear(){
		System.out.println("This year datetime:"+DateTimeInfo.getDateTimeThisYear());
	}
}
