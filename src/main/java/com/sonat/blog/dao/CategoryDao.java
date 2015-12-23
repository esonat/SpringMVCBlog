package com.sonat.blog.dao;

import com.sonat.blog.domain.Category;

public interface CategoryDao extends GenericDao<Category> {
	Category 	getCategoryByName(String categoryName);
}
