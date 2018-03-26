package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.Category;
import vocabularystudy.model.LearnPlan;
import vocabularystudy.model.LearnTask;
import vocabularystudy.model.User;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public class LearnTaskRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public LearnTaskRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public LearnTask save(LearnTask task)
    {
        Long id = (Long) getCurrentSession().save(task);
        if(id == null)
            return null;

        task.setId(id);
        return task;
    }

    public void delete(LearnTask task)
    {
        getCurrentSession().delete(task);
        getCurrentSession().flush();
    }

    public LearnTask find(User user, Category category, Date date)
    {
        Session session = getCurrentSession();
        List list = session.createCriteria(LearnTask.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .add(Restrictions.eq("learnTime", date))
                .list();

        if (list == null || list.size() == 0)
            return null;
        return (LearnTask) list.get(0);
    }


    public boolean exist(LearnTask task)
    {
        return getCurrentSession().get(LearnTask.class, task.getId()) != null;
    }

    public boolean exist(Long id)
    {
        return getCurrentSession().get(LearnTask.class, id) != null;
    }

    public boolean exist(User user, Category category, Date date)
    {
        Session session = getCurrentSession();
        List list = getCurrentSession().createCriteria(LearnTask.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("category", category))
                .add(Restrictions.eq("learnTime", date))
                .list();

        return list != null && list.size() > 0;
    }

}
