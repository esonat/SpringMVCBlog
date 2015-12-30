package com.sonat.blog.dao.hibernate;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.webflow.action.ResultEventFactory;
import org.springframework.webflow.action.ResultEventFactorySelector;
import com.sonat.blog.exception.CategoryNotFoundException;
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
	public Category getCategoryByName(String categoryName) throws DataAccessException {
		  List<Category> result = (List<Category>) this.getHibernateTemplate().findByNamedParam("select cat from Category cat where cat.name = :name", "name",categoryName);
	      if(result==null
	    	|| result.size()==0) throw new CategoryNotFoundException(0);

	      return result.get(0);
	}
}
