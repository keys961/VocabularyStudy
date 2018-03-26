package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.Category;
import vocabularystudy.model.LearnPlan;
import vocabularystudy.model.LearnWordHistory;
import vocabularystudy.model.User;

import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class LearnWordHistoryRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public LearnWordHistoryRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @CacheEvict(value = "learnWordHistoryCache", key = "(#history.user.hashCode()) ^ (#history.category.hashCode() * 65536)")
    public LearnWordHistory save(LearnWordHistory history)
    {
        Session session = getCurrentSession();
        Long id = (Long) session.save(history);

        if(id == null)
            return null;

        LearnWordHistory h = new LearnWordHistory(history.getUser(), history.getCategory(),
                history.getWord(), history.getLearnTime());
        h.setId(id);

        return h;
    }

    @CacheEvict(value = "learnWordHistoryCache", key = "(#history.user.hashCode()) ^ (#history.category.hashCode() * 65536)")
    public void delete(LearnWordHistory history)
    {
        getCurrentSession().delete(history);
    }

    @SuppressWarnings("unchecked")
    public List<LearnWordHistory> getLatestHistoryList(User user, Category category)
    {
        Session session = getCurrentSession();
        List<LearnWordHistory> learnWordHistoryList = session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .addOrder(Order.desc("learnTime"))
                .list();

        if(learnWordHistoryList == null)
            return new LinkedList<>();

        return learnWordHistoryList;
    }

    @SuppressWarnings("unchecked")
    public List<LearnWordHistory> getLatestHistoryList(User user, Category category, Long count)
    {
        Session session = getCurrentSession();
        List<LearnWordHistory> learnWordHistoryList = session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .addOrder(Order.desc("learnTime"))
                .setMaxResults(count.intValue()).list();

        if(learnWordHistoryList == null)
            return new LinkedList<>();

        return learnWordHistoryList;
    }

    @SuppressWarnings("unchecked")
    public List<LearnWordHistory> getLatestHistoryList(User user, Category category, Long count, Long offset)
    {
        Session session = getCurrentSession();
        List<LearnWordHistory> learnWordHistoryList = session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .addOrder(Order.desc("learnTime"))
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue()).list();

        if(learnWordHistoryList == null)
            return new LinkedList<>();

        return learnWordHistoryList;
    }

    @SuppressWarnings("unchecked")
    public List<LearnWordHistory> getLatestHistoryList(User user, Long offset, Long count)
    {
        Session session = getCurrentSession();
        List<LearnWordHistory> learnWordHistoryList = session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("user", user))
                .addOrder(Order.desc("learnTime"))
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue()).list();

        if(learnWordHistoryList == null)
            return new LinkedList<>();

        return learnWordHistoryList;
    }

    @Cacheable(value = "learnWordHistoryCache", key = "(#user.hashCode()) ^ (#category.hashCode() * 65536)")
    public Long count(User user, Category category)
    {
        Session session = getCurrentSession();
        return (Long)session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .setProjection(Projections.count("id"))
                .list().get(0);
    }

    @Cacheable(value = "learnWordHistoryCache", key = "(#plan.user.hashCode()) ^ (#plan.category.hashCode() * 65536)")
    public Long count(LearnPlan plan)
    {
        return count(plan.getUser(), plan.getCategory());
    }
}
