package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.repository.CategoryRepository;
import com.sonat.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category getCategoryById	(int categoryID){
		return categoryRepository.getCategoryById(categoryID);
	}
	public Category getCategoryByName(String categoryName){
		return categoryRepository.getCategoryByName(categoryName);
	}
	public List<Category> getAllCategories(){
		return categoryRepository.getAllCategories();
	}
	public void	addCategory(Category category){
		categoryRepository.addCategory(category);
	}
	public void	deleteCategory(int categoryID){
		categoryRepository.deleteCategory(categoryID);
	}
}
