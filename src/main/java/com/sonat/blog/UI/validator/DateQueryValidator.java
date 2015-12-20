package com.sonat.blog.UI.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.omg.PortableServer.ServantActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.converters.StringToBigInteger;

import com.sonat.blog.UI.model.DateQueryEnum;
import com.sonat.blog.UI.model.DateQueryModel;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.UserService;
import com.sonat.blog.validator.Username;

public class DateQueryValidator {
	public static String from;
	public static String to;
	
	public static boolean setDateValues(String dateFrom,String dateTo,DateQueryEnum dateQueryEnum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setLenient(false);
		
		if(dateFrom	== null && dateTo == null && dateQueryEnum == null) return false;
		
		if(dateQueryEnum ==null
		&&(dateFrom==null || dateTo==null)) return;
		
		if(dateQueryEnum!=null && dateQueryEnum instanceof DateQueryEnum){
			
		}
		
		if(dateFrom==null && dateTo==null) return false;
		
		try {
			Date from = sdf.parse(dateFrom);
			Date to	  =	sdf.parse(dateTo);	
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
