package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.Category;
import vocabularystudy.model.LearnCategoryHistory;
import vocabularystudy.model.User;

import java.util.List;

@Transactional
@Repository
public class LearnCategoryHistoryRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public LearnCategoryHistoryRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public LearnCategoryHistory save(LearnCategoryHistory history)
    {
        Long id = (Long) getCurrentSession().save(history);
        if(id == null)
            return null;
        history.setId(id);
        return history;
    }

    public void delete(LearnCategoryHistory history)
    {
        getCurrentSession().delete(history);
        getCurrentSession().flush();
    }

    public boolean exist(LearnCategoryHistory history)
    {
        return getCurrentSession().get(LearnCategoryHistory.class, history.getId()) != null;
    }

    public boolean exist(Long id)
    {
        return getCurrentSession().get(LearnCategoryHistory.class, id) != null;
    }

    public boolean exist(User user, Category category)
    {
        Session session = getCurrentSession();
        List list = session.createCriteria(LearnCategoryHistory.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .list();

        return list != null && list.size() > 0;
    }
}
