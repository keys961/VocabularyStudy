package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.Category;

import java.util.List;

@Repository
@Transactional
public class CategoryRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public CategoryRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Cacheable(value = "categoryCache")
    public Category find(Long id)
    {
        Session session = getCurrentSession();
        return  (Category)session.get(Category.class, id);
    }

    @Cacheable(value = "categoryCache")
    @SuppressWarnings("unchecked")
    public Category find(String category)
    {
        Session session = getCurrentSession();
        List<Category> categoryList = session.createCriteria(Category.class)
                .add(Restrictions.eq("category", category))
                .setMaxResults(1)
                .list();

        if(categoryList == null || categoryList.size() == 0)
            return null;

        return categoryList.get(0);
    }

    @Cacheable(value = "categoryCache")
    @SuppressWarnings("unchecked")
    public List<Category> findAll()
    {
        Session session = getCurrentSession();
        return session.createCriteria(Category.class)
                .list();
    }

    @CachePut(value = "categoryCache", key = "#result.category",
            condition = "#root.target != null",
            unless = "#result == null")
    public Category save(Category category)
    {
       Long id = (Long)getCurrentSession().save(category);
       Category category1 = new Category(category.getCategory());
       category1.setId(id);

       return category1;
    }

    @CacheEvict(value = "newsCategoryCache")
    public void delete(Category category)
    {
        getCurrentSession().delete(category);
    }

}
