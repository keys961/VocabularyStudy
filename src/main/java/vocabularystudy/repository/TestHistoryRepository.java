package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.TestHistory;
import vocabularystudy.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class TestHistoryRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public TestHistoryRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public TestHistory save(TestHistory history)
    {
        Long id = (Long) getCurrentSession().save(history);
        if(id == null)
            return null;

        history.setId(id);
        return history;
    }

    @SuppressWarnings("unchecked")
    public List<TestHistory> getUserTestHistory(User user)
    {
        Session session = getCurrentSession();
        List<TestHistory> list = session.createCriteria(TestHistory.class)
                .add(Restrictions.eq("user", user))
                .list();
        if(list == null)
            return new ArrayList<>(1);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<TestHistory> getUserTestHistory(User user, Long offset, Long count)
    {
        Session session = getCurrentSession();
        List<TestHistory> list = session.createCriteria(TestHistory.class)
                .add(Restrictions.eq("user", user))
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue())
                .list();
        if(list == null)
            return new ArrayList<>(1);
        return list;
    }
}
