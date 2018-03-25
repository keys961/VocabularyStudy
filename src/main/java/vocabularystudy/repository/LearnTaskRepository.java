package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.LearnTask;

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
}
