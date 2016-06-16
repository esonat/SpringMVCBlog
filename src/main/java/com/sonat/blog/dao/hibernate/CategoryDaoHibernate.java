package com.sonat.blog.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.exception.CategoryNotFoundException;

@Repository("categoryDao")
public class CategoryDaoHibernate extends GenericDaoHibernate<Category> implements CategoryDao{
    //protected Log log = LogFactory.getLog(CategoryDaoHibernate.class);

    public CategoryDaoHibernate() {
       super(Category.class);
    }

	@SuppressWarnings("unchecked")
	public Category getCategoryByName(String categoryName) throws DataAccessException {
		  List<Category> result = (List<Category>) this.getHibernateTemplate().findByNamedParam("select cat from Category cat where cat.name = :name", "name",categoryName);
	      if(result==null
	    	|| result.size()==0) throw new CategoryNotFoundException(0);

	      return result.get(0);
	}
}
