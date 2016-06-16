package com.sonat.blog.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.domain.Category;
import com.sonat.blog.exception.CategoryNotFoundException;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional
@Rollback(true)
public class testCategoryService {

	@Autowired
	private CategoryService categoryService;

	private static final int 	VALID_CATEGORY_ID=1;
	private static final String VALID_CATEGORY_NAME="java";
	private static final int 	INVALID_CATEGORY_ID=1000;
	private static final String INVALID_CATEGORY_NAME="invalidName";
	private static final String VALID_TEST_NAME="test";

	@Test
	@Transactional
	@Rollback(true)
	public void testAddValidCategory(){
		Category category=new Category(VALID_TEST_NAME);
		categoryService.addCategory(category);
	}

	@Test(expected=DataAccessException.class)
	@Transactional
	@Rollback(true)
	public void testAddInvalidCategory(){
		Category category=new Category("");
		categoryService.addCategory(category);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetCategoryByValidId(){
		Category category=categoryService.getCategoryById(VALID_CATEGORY_ID);
		Assert.assertNotNull(category);
		Assert.assertEquals(category.getID(),VALID_CATEGORY_ID);
	}

	@Test(expected=CategoryNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetCategoryByInvalidId(){
		Category category=categoryService.getCategoryById(INVALID_CATEGORY_ID);
		Assert.assertNull(category);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetCategoryByValidName(){
		Category category=categoryService.getCategoryByName(VALID_CATEGORY_NAME);
		Assert.assertNotNull(category);
		Assert.assertEquals(category.getName(),VALID_CATEGORY_NAME);
	}

	@Test(expected=CategoryNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetCategoryByInvalidName(){
		Category category=categoryService.getCategoryByName(INVALID_CATEGORY_NAME);
		Assert.assertNull(category);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllCategories(){
		List<Category> list=categoryService.getAllCategories();
		Assert.assertNotNull(list);
	}

}