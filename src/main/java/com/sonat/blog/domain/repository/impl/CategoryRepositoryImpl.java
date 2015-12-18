package com.sonat.blog.domain.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.repository.CategoryRepository;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	
	public void addCategory(Category category) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(category);
		session.getTransaction().commit();
	}
	
	public void deleteCategory(int categoryID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Category C WHERE C.ID: =categoryID");
		if(query.list()==null ||
		   query.list().size()==0) return;
		
		Category category=(Category)query.uniqueResult();
		
		session.beginTransaction();
		session.delete(category);
		session.getTransaction().commit();	
	}


	@SuppressWarnings("unchecked")
	public List<Category> getAllCategories() {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Category");
		
		if(query.list()==null ||
		   query.list().size()==0) return null;
		
		return (List<Category>)query.list();
	}

	public Category getCategoryById(int categoryID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Category C where C.ID= :categoryID");
		query.setParameter("categoryID",categoryID);
		
		if(query.list()==null ||
		   query.list().size()==0) throw new CategoryNotFoundException(categoryID);

		return (Category)query.uniqueResult();
	}

	public Category getCategoryByName(String categoryName) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Category C WHERE C.name= :categoryName");
		query.setParameter("categoryName", categoryName);
		
		if(query.list()==null ||
		   query.list().size()==0) return null;
		
		Category category=(Category)query.uniqueResult();
		return category;
	}



}
