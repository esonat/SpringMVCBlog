package com.sonat.blog.dao.hibernate;

import com.sonat.blog.dao.GenericDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.DomainObject;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;

public class GenericDaoHibernate<T extends DomainObject> extends HibernateDaoSupport implements GenericDao<T> {
	private Class<T> type;

    public GenericDaoHibernate(Class<T> type) {
        super();
        this.type = type;
    }

    public T get(int id) {
        return (T) getHibernateTemplate().get(type, id);
    }

    public List<T> getAll() {
        return getHibernateTemplate().loadAll(type);
    }
    
    public void save(T object) {
    	Session session=getHibernateTemplate().getSessionFactory().openSession();
    	session.beginTransaction();
    	
    	session.save(object);
    	//session.flush();
    	
    	session.getTransaction().commit();
/*		getHibernateTemplate().save(object);
		getHibernateTemplate().flush();
*/
    	session.close();
    	}

    public void delete(T object) {
        getHibernateTemplate().delete(object);
    }


    public void indexEntity(T object) {
    	FullTextSession fullTextSession = Search.getFullTextSession(this.getSession()); 
    	fullTextSession.index(object);
    }

    public void indexAllItems() {
        FullTextSession fullTextSession = Search.getFullTextSession(this.getSession());
        ScrollableResults results = fullTextSession.createCriteria(this.type).scroll(ScrollMode.FORWARD_ONLY);
        int counter = 0, numItemsInGroup = 10;
        while (results.next()) {
            fullTextSession.index(results.get(0));
            if (counter++ % numItemsInGroup == 0) {
                fullTextSession.flushToIndexes();
                fullTextSession.clear();
            }
        }
    }


    @Autowired
    public void setupSessionFactory(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

}
