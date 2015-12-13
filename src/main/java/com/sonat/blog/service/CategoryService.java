package com.sonat.blog.service;

import java.util.List;
import com.sonat.blog.domain.Category;

public interface CategoryService {
	Category 		getCategoryById	(int categoryID);	
	Category 		getCategoryByName(String categoryName);	
	List<Category> 	getAllCategories();	
	void			addCategory		(Category category);
	void			deleteCategory	(int categoryID);
}
