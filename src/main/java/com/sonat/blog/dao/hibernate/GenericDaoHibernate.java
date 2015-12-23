package com.sonat.blog.dao.hibernate;

import com.sonat.blog.dao.GenericDao;
import com.sonat.blog.domain.DomainObject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericDaoHibernate<T extends DomainObject> extends HibernateDaoSupport implements GenericDao<T> {
	private Class<T> type;

    public GenericDaoHibernate(Class<T> type) {
        super();
        this.type = type;
    }

    public T get(Long id) {
        return (T) getHibernateTemplate().get(type, id);
    }

    public List<T> getAll() {
        return getHibernateTemplate().loadAll(type);
    }

    public void save(T object) {
        getHibernateTemplate().save(object);
        getHibernateTemplate().flush();
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
