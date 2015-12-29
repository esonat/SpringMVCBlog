package com.sonat.blog.dao.hibernate;

import com.sonat.blog.domain.*;

import junit.framework.Assert;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
public class testCategoryDaoHibernate {
	@Autowired
	private CategoryDao categoryDao;
		
	private static final String VALID_CATEGORY_NAME		="java";
	private static final String INVALID_CATEGORY_NAME	="example";
	private static final int 	VALID_CATEGORY_ID=1;
	private static final int 	INVALID_CATEGORY_ID=1000;
	
	
	
	@Ignore
	public int getCategoryCount(){
		if(categoryDao.getAll()==null) 
			return 0;
		
		return categoryDao.getAll().size();
	}
	@Ignore
	public int getValidCategoryID(){
		if(categoryDao.getAll()==null) return -1;
		return categoryDao.getAll().get(0).getID();
	}
	
	@Test
	public void testAddCategory(){
		Category category=new Category("newcategory");
		int oldcount=getCategoryCount();
		
		categoryDao.save(category);
		int newcount=getCategoryCount();
		
		Assert.assertEquals(newcount,oldcount+1);		
	}
	@Test
	public void testValidGetCategory(){
		int validID=getValidCategoryID();
		Category category=categoryDao.get(validID);
		
		Assert.assertNotNull(category);
		Assert.assertEquals(validID,category.getID());
	}
	@Test(expected=DataAccessException.class)
	public void testInvalidGetCategory(){
		Category category=categoryDao.get(INVALID_CATEGORY_ID);
		
		Assert.assertNull(category);
	}
	@Test
	public void testValidDelete(){
		int old=getCategoryCount();
		Category category=categoryDao.get(getValidCategoryID());
		
		categoryDao.delete(category);
	}
	


}