package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.Category;
import vocabularystudy.model.LearnPlan;
import vocabularystudy.model.User;

import java.util.List;

@Repository
@Transactional
public class LearnPlanRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public LearnPlanRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }


    public LearnPlan save(LearnPlan plan)
    {
        Session session = getCurrentSession();
        Long id = (Long)session.save(plan);
        if(id == null)
            return null;

        LearnPlan p = new LearnPlan(plan.getUser(), plan.getCategory(), plan.getStartTime(), plan.getEndTime());
        p.setId(id);

        return p;
    }

    public LearnPlan update(LearnPlan plan)
    {
        getCurrentSession().update(plan);
        getCurrentSession().flush();
        return plan;
    }


    @SuppressWarnings("unchecked")
    public void delete(LearnPlan plan)
    {
        Session session = getCurrentSession();
        List<LearnPlan> plans = session.createCriteria(LearnPlan.class)
                .add(Restrictions.eq("category", plan.getCategory()))
                .add(Restrictions.eq("user", plan.getUser()))
                .list();

        for(LearnPlan p : plans)
            session.delete(p);
        session.flush();
    }

    @SuppressWarnings("unchecked")
    public LearnPlan getLearnPlan(User user)
    {
        Session session = getCurrentSession();
        List<LearnPlan> plans = session.createCriteria(LearnPlan.class)
                .add(Restrictions.eq("user", user))
                .addOrder(Order.desc("startTime"))
                .setMaxResults(1)
                .list();
        if(plans == null || plans.size() == 0)
            return null;
        return plans.get(0);
    }


    public boolean exist(LearnPlan plan)
    {
        List list = getCurrentSession().createCriteria(LearnPlan.class)
                .add(Restrictions.eq("user", plan.getUser()))
                .add(Restrictions.eq("category", plan.getCategory())).list();
        return list != null && list.size() > 0;
    }

    public boolean exist(User user, Category category)
    {
        List list = getCurrentSession().createCriteria(LearnPlan.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category)).list();
        return list != null && list.size() > 0;
    }

}
