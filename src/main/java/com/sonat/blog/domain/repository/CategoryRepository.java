package com.sonat.blog.domain.repository;

import java.util.List;
import com.sonat.blog.domain.Category;

public interface CategoryRepository {
	void			addCategory		(Category category);	
	void			deleteCategory	(int categoryID);	
	List<Category> 	getAllCategories();	
	Category 		getCategoryById	(int categoryID);
	Category 		getCategoryByName(String categoryName);
}
