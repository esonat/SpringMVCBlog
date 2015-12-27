package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.hibernate.CategoryDaoHibernate;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.repository.CategoryRepository;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService{
//	@Autowired
//	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryDao categoryDao;
	
	public Category getCategoryById	(int categoryID)
	throws CategoryNotFoundException{
		//return categoryRepository.getCategoryById(categoryID);
		return categoryDao.get(categoryID);
	}
	public Category getCategoryByName(String categoryName){
		//return categoryRepository.getCategoryByName(categoryName);
		return categoryDao.getCategoryByName(categoryName);
	}
	public List<Category> getAllCategories(){
		//return categoryRepository.getAllCategories();
		List<Category> list;
		try{
			list=categoryDao.getAll();
		}catch(Exception e){
			return null;
		}
		return list;
	}
	
	@Transactional(rollbackFor=DataAccessException.class, readOnly=false, timeout=30, propagation=Propagation.SUPPORTS, isolation=Isolation.DEFAULT)
	public void	addCategory(Category category) throws DataAccessException{
		categoryDao.save(category);
	}
	public void	deleteCategory(int categoryID){
		//categoryRepository.deleteCategory(categoryID);
		Category category=categoryDao.get(categoryID);
		categoryDao.delete(category);
	}
}
