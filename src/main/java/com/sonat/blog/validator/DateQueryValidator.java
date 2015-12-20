package com.sonat.blog.validator;

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

public class UsernameValidator implements ConstraintValidator<Username,String>{
	@Autowired
	private UserService userService;
	
	public void initialize(Username constraintAnnotation){
	}
	
	public boolean isValid(String value,ConstraintValidatorContext context){
		User user=null;
		try{
			user=userService.getUserByUsername(value);
		}catch(Exception e){
			return true;
		}
		if(user!=null){
			return false;
		}
		return true;
	}
}



public class DateQueryValidator {
	public static String dateFrom;
	public static String dateTo;
	public static boolean isValid;
	
	public static void setDateValues(DateQueryModel dateQueryModel){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setLenient(false);
		
		String dateFrom=dateQueryModel.getDateFrom();
		String dateTo=dateQueryModel.getDateTo();
		DateQueryEnum dateQueryEnum=dateQueryModel.getDateQueryEnum();
		
		//if(dateFrom	== null && dateTo == null && dateQueryEnum == null) isValid=false;
		
		if(dateQueryEnum ==null
		&&(dateFrom==null || dateTo==null)) isValid=false;
		
		if(dateQueryEnum!=null &&
		   dateQueryEnum instanceof DateQueryEnum){
			isValid=true;
			dateFrom=
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
