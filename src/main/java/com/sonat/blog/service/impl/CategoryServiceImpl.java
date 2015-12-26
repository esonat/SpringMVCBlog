package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.hibernate.CategoryDaoHibernate;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.repository.CategoryRepository;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.service.CategoryService;

@Service(value = "categoryService")
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
	public void	addCategory(Category category){
		//categoryRepository.addCategory(category);
		categoryDao.save(category);
	}
	public void	deleteCategory(int categoryID){
		//categoryRepository.deleteCategory(categoryID);
		Category category=categoryDao.get(categoryID);
		categoryDao.delete(category);
	}
}
