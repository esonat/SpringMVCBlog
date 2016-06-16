package com.sonat.blog.UI.validator;

import org.junit.Test;

import com.sonat.blog.util.datetime.DateFormatValidator;
import com.sonat.blog.util.datetime.DateTimeConstants;

public class DateFormatValidatorTests {

	@Test
	public void testDateFormatValidator(){
		System.out.println(DateFormatValidator.Parse("2015-01-0112:00:00",DateTimeConstants.DATETIME_FORMAT));
	}
}
