package com.sonat.blog.dao.hibernate;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.domain.Category;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Repository("categoryDao")
public class CategoryDaoHibernate extends GenericDaoHibernate<Category> implements CategoryDao{
    //protected Log log = LogFactory.getLog(CategoryDaoHibernate.class);

    public CategoryDaoHibernate() {
       super(Category.class);
    }

	@Override
	@SuppressWarnings("unchecked")
	public Category getCategoryByName(String categoryName)  throws DataAccessException {
		  final List<Category> results = (List<Category>) this.getHibernateTemplate().findByNamedParam("select cat from Category cat where cat.name = :name", "name",categoryName);
	       return results.isEmpty() ? null : results.get(0);
	}   

}
