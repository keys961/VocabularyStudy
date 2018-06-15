package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.*;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class LearnTaskItemRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public LearnTaskItemRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public LearnTaskItem save(LearnTaskItem item)
    {
        Long id = (Long) getCurrentSession().save(item);
        if(id == null)
            return null;
        item.setId(id);
        return item;
    }

    public List<LearnTaskItem> saveList(List<LearnTaskItem> items)
    {
        for(LearnTaskItem item : items)
        {
            Long id = (Long) getCurrentSession().save(item);
            if(id != null)
                item.setId(id);
        }

        return items;
    }

    @SuppressWarnings("unchecked")
    public List<LearnTaskItem> getTodayLearnTaskItemList(LearnTask task)
    {
        Session session = getCurrentSession();

        List<Vocabulary> learnedList = session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("user", task.getUser()))
                .add(Restrictions.eq("category", task.getCategory()))
                .setProjection(Projections.property("word"))
                .list();

        List<LearnTaskItem> todoList;
        if(learnedList.isEmpty())
            todoList = session.createCriteria(LearnTaskItem.class)
                    .add(Restrictions.eq("learnTask", task))
                    .list();
        else
            todoList = session.createCriteria(LearnTaskItem.class)
                .add(Restrictions.eq("learnTask", task))
                .add(Restrictions.not(Restrictions.in("word", learnedList)))
                .list();

        if(todoList == null)
            return new ArrayList<>(1);

        return todoList;
    }
}
