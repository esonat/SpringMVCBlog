package com.sonat.blog.UI.validator;

import java.text.ParseException;
import java.util.Date;

import com.sonat.blog.UI.model.DateQueryEnum;
import com.sonat.blog.UI.model.DateQueryModel;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.datetime.DateFormatValidator;
import com.sonat.blog.util.datetime.DateTimeConstants;
import com.sonat.blog.util.datetime.DateTimeInfo;
import com.sonat.blog.validator.Username;

public class DateQueryValidator {
	public static Date from;
	public static Date to;
	
	public static boolean setDateValues(String dateFrom,String dateTo,DateQueryEnum dateQueryEnum){
		//IF ALL OF THEM NULL RETURN FALSE
		
		if(dateQueryEnum!=null){
				if(dateQueryEnum instanceof DateQueryEnum){
					switch (dateQueryEnum) {
					case today:
						from=	DateTimeInfo.getDateTimeToday();
						to	=	DateTimeInfo.getDateTimeNow();
						return true;
					case thisweek:
						from=	DateTimeInfo.getDateTimeThisWeek();
						to	=	DateTimeInfo.getDateTimeNow();
						return true;
					case thismonth:
						from=	DateTimeInfo.getDateTimeThisMonth();
						to	=	DateTimeInfo.getDateTimeNow();
						return true;
					case thisyear:
						from=	DateTimeInfo.getDateTimeThisYear();
						to	=	DateTimeInfo.getDateTimeNow();
						return true;
					default:
						return false;
				}
			}else if(!(dateQueryEnum instanceof DateQueryEnum))
				return false;
		}else if(dateQueryEnum==null){
			
			if(dateFrom==null && dateTo==null) return false;
			if(dateFrom==null && dateTo!=null) return false;
			
			if(dateFrom!=null && dateTo==null){
				Date fromValid=DateFormatValidator.Parse(dateFrom,DateTimeConstants.DATETIME_FORMAT);
				if(fromValid==null)
					return false;
				
				from	= fromValid;
				to		= DateTimeInfo.getDateTimeNow();		
				return true;
			}
			if(dateFrom!=null && dateTo!=null){
				Date fromValid 	=	DateFormatValidator.Parse(dateFrom,DateTimeConstants.DATETIME_FORMAT);
				Date toValid 	=	DateFormatValidator.Parse(dateTo  ,DateTimeConstants.DATETIME_FORMAT);
			
				if(fromValid==null || toValid==null) return false;
				
				from	=	fromValid;
				to		=	toValid;
				return true;
			}
		}
		return false;
	}
}
