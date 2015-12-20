package com.sonat.blog.UI.validator;

import java.text.ParseException;
import org.apache.commons.io.output.ThresholdingOutputStream;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hibernate.engine.IdentifierValue;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.mockito.internal.matchers.Not;
import org.omg.PortableServer.ServantActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.converters.StringToBigInteger;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.sonat.blog.UI.model.DateQueryEnum;
import com.sonat.blog.UI.model.DateQueryModel;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.datetime.DateFormatValidator;
import com.sonat.blog.util.datetime.DateTimeConstants;
import com.sonat.blog.util.datetime.DateTimeInfo;
import com.sonat.blog.validator.Username;

public class DateQueryValidator {
	public static String from;
	public static String to;
	
	public static boolean setDateValues(String dateFrom,String dateTo,DateQueryEnum dateQueryEnum){
		//IF ALL OF THEM NULL RETURN FALSE
		if(dateFrom			== null 	
		&& dateTo 			== null	 
		&& dateQueryEnum	== null) 	return false;
		
		if(dateQueryEnum 	== null 
		&& dateFrom			== null) 	return false;
		
		if(dateQueryEnum!=null 	
		&& 	!(dateQueryEnum instanceof DateQueryEnum)) 	return false;
		
		if(dateQueryEnum!=null && dateQueryEnum instanceof DateQueryEnum){
			switch (dateQueryEnum) {
				case today:
					from=	DateTimeInfo.getDateTimeToday();
					to	=	DateTimeInfo.getDateTimeNow();
				break;
				case thisweek:
					from=	DateTimeInfo.getDateTimeThisWeek();
					to	=	DateTimeInfo.getDateTimeNow();
				break;
				case thismonth:
					from=	DateTimeInfo.getDateTimeThisMonth();
					to	=	DateTimeInfo.getDateTimeNow();
				break;
				case thisyear:
					from=	DateTimeInfo.getDateTimeThisYear();
					to	=	DateTimeInfo.getDateTimeNow();
				break;
				default:
					break;
			}
		}
		
		if(dateFrom==null && dateTo!=null) return false;
		if(dateFrom!=null && dateTo==null){
			try{
				from	= DateFormatValidator.Parse(dateFrom,DateTimeConstants.DATETIME_FORMAT);
				to		= DateTimeInfo.getDateTimeNow();
			}catch(ParseException e){
				return false;
			}			
		}
		if(dateFrom!=null && dateTo!=null){
			try{
				from	=	DateFormatValidator.Parse(dateFrom,DateTimeConstants.DATETIME_FORMAT);
				to		=	DateFormatValidator.Parse(dateFrom,DateTimeConstants.DATETIME_FORMAT);
			}catch(ParseException e){
				return false;
			}
		}		
		return true;
	}
}
